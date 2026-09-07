import { useState } from 'react';
import { useParams } from 'react-router-dom';
import { Copy, ChevronRight, Play, CheckCircle, XCircle, Clock, Database, Lightbulb, BookOpen, Code2, MessageSquare, Search, Download } from 'lucide-react';
import { useProblem, useProblemMastery, useUpdateMastery } from '@/hooks/useQueries';
import { useEditorStore, useAITutorStore, useMasteryStore } from '@/store';
import { MarkdownRenderer } from '@/components/ui/MarkdownRenderer';
import { CodeEditor } from '@/components/editor/CodeEditor';
import { TestResults } from '@/components/problem/TestResults';
import { AITutorPanel } from '@/components/ai/AITutorPanel';
import { MasteryIndicator } from '@/components/problem/MasteryIndicator';
import clsx from 'clsx';
import type { ProblemChapterDto, TestCaseDto, SolutionDto } from '@/types';

const TABS = [
  { id: 'overview', label: 'Overview', icon: BookOpen },
  { id: 'playground', label: 'Playground', icon: Code2 },
  { id: 'solutions', label: 'Solutions', icon: Lightbulb },
  { id: 'tests', label: 'Test Cases', icon: Database },
  { id: 'ai', label: 'AI Tutor', icon: MessageSquare },
] as const;

export function ProblemPage() {
  const { slug } = useParams<{ slug: string }>();
  const { data: problem, isLoading, error } = useProblem(slug!);
  const { data: mastery } = useProblemMastery(slug!);
  const updateMastery = useUpdateMastery();
  const { language, code, setLanguage, setCode, reset } = useEditorStore();
  const { open: openAI, problemId: aiProblemId } = useAITutorStore();
  const { updateProblemMastery: updateLocalMastery } = useMasteryStore();
  const [activeTab, setActiveTab] = useState('overview');
  const [executionJobId, setExecutionJobId] = useState<string | null>(null);
  const [isExecuting, setIsExecuting] = useState(false);
  const [showSolution, setShowSolution] = useState(false);
  
  const handleRunCode = async (codeToRun: string) => {
    if (!slug) return;
    setIsExecuting(true);
    try {
      // This would call the execution API
      // For now, just simulate
      setExecutionJobId('simulated-job');
    } catch (err) {
      console.error('Execution failed:', err);
    } finally {
      setIsExecuting(false);
    }
  };
  
  const handleMarkViewed = () => {
    updateMastery.mutate({
      entityType: 'PROBLEM',
      entityId: problem?.id!,
      data: { viewed: true, explanationViewed: activeTab !== 'overview' },
    });
    updateLocalMastery(problem?.id!, 'LEARNING');
  };
  
  if (isLoading) {
    return (
      <div className="flex items-center justify-center h-64">
        <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-primary-600" />
      </div>
    );
  }
  
  if (error || !problem) {
    return (
      <div className="text-center py-12">
        <h2 className="text-xl font-semibold text-slate-900 dark:text-slate-100 mb-2">Problem not found</h2>
        <p className="text-slate-500 dark:text-slate-400">The problem you're looking for doesn't exist.</p>
      </div>
    );
  }
  
  return (
    <div className="space-y-6">
      {/* Header */}
      <div className="flex flex-col sm:flex-row sm:items-start sm:justify-between gap-4">
        <div className="flex items-start gap-4">
          <div className="p-3 bg-primary-100 dark:bg-primary-900/30 rounded-xl">
            <Code2 className="w-8 h-8 text-primary-600 dark:text-primary-400" />
          </div>
          <div>
            <div className="flex items-center gap-2 mb-1">
              <h1 className="text-2xl font-bold text-slate-900 dark:text-slate-100">{problem.title}</h1>
              <span className={clsx(
                'px-2 py-0.5 rounded text-xs font-medium',
                problem.difficulty === 'EASY' && 'bg-green-100 text-green-800 dark:bg-green-900 dark:text-green-200',
                problem.difficulty === 'MEDIUM' && 'bg-yellow-100 text-yellow-800 dark:bg-yellow-900 dark:text-yellow-200',
                problem.difficulty === 'HARD' && 'bg-red-100 text-red-800 dark:bg-red-900 dark:text-red-200',
              )}>
                {problem.difficulty}
              </span>
            </div>
            <p className="text-slate-500 dark:text-slate-400 text-sm">
              {problem.patterns.map(p => p.name).join(', ')} • 
              {problem.algorithms.map(a => a.name).join(', ')}
            </p>
          </div>
        </div>
        
        <div className="flex items-center gap-2">
          <MasteryIndicator status={mastery?.status || 'NOT_STARTED'} score={mastery?.score} />
          {problem.externalUrl && (
            <a
              href={problem.externalUrl}
              target="_blank"
              rel="noopener noreferrer"
              className="p-2 rounded-lg hover:bg-slate-100 dark:hover:bg-slate-800 text-slate-500 dark:text-slate-400 transition-colors"
              aria-label="View on original site"
            >
              <Search className="w-5 h-5" />
            </a>
          )}
        </div>
      </div>
      
      {/* Tabs */}
      <div className="border-b border-slate-200 dark:border-slate-700">
        <nav className="flex overflow-x-auto" aria-label="Problem sections">
          {TABS.map((tab) => (
            <button
              key={tab.id}
              onClick={() => {
                setActiveTab(tab.id);
                if (tab.id === 'overview') handleMarkViewed();
              }}
              className={clsx(
                'tab whitespace-nowrap flex items-center gap-2',
                activeTab === tab.id && 'tab-active'
              )}
              aria-current={activeTab === tab.id ? 'page' : undefined}
            >
              <tab.icon className="w-4 h-4" />
              {tab.label}
            </button>
          ))}
        </nav>
      </div>
      
      {/* Tab Content */}
      <div className="card overflow-hidden">
        {activeTab === 'overview' && (
          <ProblemOverview problem={problem} onMarkViewed={handleMarkViewed} />
        )}
        {activeTab === 'playground' && (
          <CodeEditor
            problem={problem}
            language={language}
            code={code}
            onLanguageChange={setLanguage}
            onCodeChange={setCode}
            onRun={handleRunCode}
            isExecuting={isExecuting}
            executionJobId={executionJobId}
          />
        )}
        {activeTab === 'solutions' && (
          <ProblemSolutions problem={problem} showSolution={showSolution} onToggleSolution={setShowSolution} />
        )}
        {activeTab === 'tests' && (
          <ProblemTestCases problem={problem} />
        )}
        {activeTab === 'ai' && (
          <AITutorPanel problem={problem} />
        )}
      </div>
    </div>
  );
}

function ProblemOverview({ problem, onMarkViewed }: { problem: ProblemChapterDto; onMarkViewed: () => void }) {
  const [expandedSections, setExpandedSections] = useState<Set<string>>(new Set(['description', 'recognition']));
  
  const toggleSection = (section: string) => {
    setExpandedSections((prev) => {
      const next = new Set(prev);
      if (next.has(section)) next.delete(section);
      else next.add(section);
      return next;
    });
  };
  
  return (
    <div className="p-6 space-y-6 scrollbar-thin max-h-[calc(100vh-280px)] overflow-y-auto">
      <button
        onClick={onMarkViewed}
        className="text-sm text-primary-600 dark:text-primary-400 hover:underline"
      >
        Mark as viewed
      </button>
      
      {/* Problem Statement */}
      <CollapsibleSection title="Problem Statement" isExpanded={expandedSections.has('description')} onToggle={() => toggleSection('description')}>
        <MarkdownRenderer content={problem.description} />
        {problem.externalUrl && (
          <p className="mt-4 text-sm text-slate-500 dark:text-slate-400">
            Original source: <a href={problem.externalUrl} target="_blank" rel="noopener noreferrer" className="text-primary-600 hover:underline">{problem.externalUrl}</a>
          </p>
        )}
      </CollapsibleSection>
      
      {/* Recognition Signals */}
      <CollapsibleSection title="Recognition Signals" isExpanded={expandedSections.has('recognition')} onToggle={() => toggleSection('recognition')}>
        <div className="space-y-2">
          {problem.patterns.flatMap(p => p.recognitionSignals || []).map((signal, i) => (
            <div key={i} className="flex items-start gap-3 p-3 bg-slate-50 dark:bg-slate-800/50 rounded-lg">
              <Lightbulb className="w-5 h-5 text-amber-500 flex-shrink-0 mt-0.5" />
              <span className="text-sm text-slate-700 dark:text-slate-300">{signal}</span>
            </div>
          ))}
        </div>
      </CollapsibleSection>
      
      {/* Learning Objectives */}
      {problem.learningObjectives.length > 0 && (
        <CollapsibleSection title="Learning Objectives" defaultExpanded>
          <ol className="space-y-2 list-decimal list-inside">
            {problem.learningObjectives.map((obj, i) => (
              <li key={i} className="text-sm text-slate-700 dark:text-slate-300">
                <span className="font-medium">[{obj.bloomLevel}]</span> {obj.description}
              </li>
            ))}
          </ol>
        </CollapsibleSection>
      )}
      
      {/* Prerequisites */}
      {problem.prerequisites.length > 0 && (
        <CollapsibleSection title="Prerequisites" defaultExpanded>
          <div className="flex flex-wrap gap-2">
            {problem.prerequisites.map((prereq) => (
              <span key={prereq.id} className="px-3 py-1 text-sm bg-slate-100 dark:bg-slate-800 text-slate-700 dark:text-slate-300 rounded-full">
                {prereq.name}
              </span>
            ))}
          </div>
        </CollapsibleSection>
      )}
      
      {/* Patterns Used */}
      <CollapsibleSection title="Patterns & Algorithms" defaultExpanded>
        <div className="space-y-4">
          <div>
            <h4 className="font-medium text-slate-700 dark:text-slate-300 mb-2">Patterns</h4>
            <div className="flex flex-wrap gap-2">
              {problem.patterns.map((p) => (
                <Link key={p.id} to={`/pattern/${p.slug}`} className="px-3 py-1 text-sm bg-purple-100 dark:bg-purple-900 text-purple-700 dark:text-purple-300 rounded-full hover:bg-purple-200 dark:hover:bg-purple-800 transition-colors">
                  {p.name}
                </Link>
              ))}
            </div>
          </div>
          <div>
            <h4 className="font-medium text-slate-700 dark:text-slate-300 mb-2">Algorithms</h4>
            <div className="flex flex-wrap gap-2">
              {problem.algorithms.map((a) => (
                <Link key={a.id} to={`/algorithm/${a.slug}`} className="px-3 py-1 text-sm bg-blue-100 dark:bg-blue-900 text-blue-700 dark:text-blue-300 rounded-full hover:bg-blue-200 dark:hover:bg-blue-800 transition-colors">
                  {a.name}
                </Link>
              ))}
            </div>
          </div>
        </div>
      </CollapsibleSection>
      
      {/* Data Structures */}
      {problem.dataStructures.length > 0 && (
        <CollapsibleSection title="Data Structures" defaultExpanded>
          <div className="flex flex-wrap gap-2">
            {problem.dataStructures.map((ds) => (
              <Link key={ds.id} to={`/data-structure/${ds.slug}`} className="px-3 py-1 text-sm bg-green-100 dark:bg-green-900 text-green-700 dark:text-green-300 rounded-full hover:bg-green-200 dark:hover:bg-green-800 transition-colors">
                {ds.name}
              </Link>
            ))}
          </div>
        </CollapsibleSection>
      )}
      
      {/* Templates */}
      {problem.templates.length > 0 && (
        <CollapsibleSection title="Templates" defaultExpanded>
          <div className="space-y-2">
            {problem.templates.map((t) => (
              <Link key={t.id} to={`/template/${t.slug}`} className="flex items-center gap-3 p-3 bg-slate-50 dark:bg-slate-800/50 rounded-lg hover:bg-slate-100 dark:hover:bg-slate-800 transition-colors">
                <Code2 className="w-5 h-5 text-slate-400" />
                <div>
                  <p className="font-medium text-sm text-slate-900 dark:text-slate-100">{t.name}</p>
                  <p className="text-xs text-slate-500 dark:text-slate-400">{t.language}</p>
                </div>
              </Link>
            ))}
          </div>
        </CollapsibleSection>
      )}
      
      {/* Related Problems */}
      {problem.relatedProblems.length > 0 && (
        <CollapsibleSection title="Related Problems" defaultExpanded>
          <div className="space-y-2">
            {problem.relatedProblems.map((p) => (
              <Link key={p.id} to={`/problem/${p.slug}`} className="flex items-center justify-between p-3 bg-slate-50 dark:bg-slate-800/50 rounded-lg hover:bg-slate-100 dark:hover:bg-slate-800 transition-colors">
                <div>
                  <p className="font-medium text-sm text-slate-900 dark:text-slate-100">{p.title}</p>
                  <p className="text-xs text-slate-500 dark:text-slate-400">{p.difficulty}</p>
                </div>
                <ChevronRight className="w-4 h-4 text-slate-400" />
              </Link>
            ))}
          </div>
        </CollapsibleSection>
      )}
      
      {/* References */}
      {problem.references.length > 0 && (
        <CollapsibleSection title="References" defaultExpanded>
          <ul className="space-y-2">
            {problem.references.map((ref) => (
              <li key={ref.id} className="text-sm">
                <a href={ref.url} target="_blank" rel="noopener noreferrer" className="text-primary-600 hover:underline flex items-center gap-2">
                  {ref.title}
                  <span className="text-slate-400">({ref.type})</span>
                  {ref.verifies && <span className="text-slate-400">- {ref.verifies}</span>}
                </a>
              </li>
            ))}
          </ul>
        </CollapsibleSection>
      )}
    </div>
  );
}

function CollapsibleSection({ 
  title, 
  isExpanded, 
  onToggle, 
  defaultExpanded = false,
  children 
}: { 
  title: string; 
  isExpanded?: boolean; 
  onToggle?: () => void; 
  defaultExpanded?: boolean;
  children: React.ReactNode;
}) {
  const [expanded, setExpanded] = useState(defaultExpanded);
  const isOpen = isExpanded ?? expanded;
  
  return (
    <div className="border border-slate-200 dark:border-slate-700 rounded-lg overflow-hidden">
      <button
        onClick={() => { onToggle?.(); setExpanded(!isOpen); }}
        className="w-full flex items-center justify-between p-4 bg-slate-50 dark:bg-slate-800/50 hover:bg-slate-100 dark:hover:bg-slate-800 transition-colors text-left"
      >
        <h3 className="font-semibold text-slate-900 dark:text-slate-100">{title}</h3>
        <ChevronRight className={clsx('w-5 h-5 text-slate-400 transition-transform', isOpen && 'rotate-90')} />
      </button>
      {isOpen && <div className="p-4">{children}</div>}
    </div>
  );
}

function ProblemSolutions({ problem, showSolution, onToggleSolution }: { problem: ProblemChapterDto; showSolution: boolean; onToggleSolution: (show: boolean) => void }) {
  const [selectedApproach, setSelectedApproach] = useState<'WORST' | 'BETTER' | 'BEST'>('WORST');
  
  const approaches = problem.solutions.reduce((acc, s) => {
    if (!acc[s.approach]) acc[s.approach] = [];
    acc[s.approach].push(s);
    return acc;
  }, {} as Record<string, SolutionDto[]>);
  
  return (
    <div className="p-6 space-y-6">
      <div className="flex items-center justify-between">
        <h2 className="text-lg font-semibold text-slate-900 dark:text-slate-100">Solutions</h2>
        <div className="flex items-center gap-2">
          <span className="text-sm text-slate-500 dark:text-slate-400">Show optimal:</span>
          <label className="relative inline-flex items-center cursor-pointer">
            <input
              type="checkbox"
              checked={showSolution}
              onChange={(e) => onToggleSolution(e.target.checked)}
              className="sr-only peer"
            />
            <div className="w-11 h-6 bg-slate-200 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-primary-300 dark:peer-focus:ring-primary-800 rounded-full peer dark:bg-slate-700 peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all dark:border-slate-600 peer-checked:bg-primary-600"></div>
          </label>
        </div>
      </div>
      
      <div className="flex gap-2 border-b border-slate-200 dark:border-slate-700">
        {['WORST', 'BETTER', 'BEST'].map((approach) => (
          <button
            key={approach}
            onClick={() => setSelectedApproach(approach as any)}
            className={clsx(
              'tab px-4 py-2',
              selectedApproach === approach && 'tab-active'
            )}
          >
            {approach}
          </button>
        ))}
      </div>
      
      <div className="space-y-6">
        {approaches[selectedApproach]?.map((solution) => (
          <SolutionCard solution={solution} showCode={showSolution || selectedApproach !== 'BEST'} />
        ))}
      </div>
    </div>
  );
}

function SolutionCard({ solution, showCode }: { solution: SolutionDto; showCode: boolean }) {
  const [codeVisible, setCodeVisible] = useState(showCode);
  
  return (
    <div className="border border-slate-200 dark:border-slate-700 rounded-lg overflow-hidden">
      <div className="p-4 bg-slate-50 dark:bg-slate-800/50 border-b border-slate-200 dark:border-slate-700">
        <div className="flex items-center justify-between">
          <div>
            <h4 className="font-semibold text-slate-900 dark:text-slate-100">{solution.name}</h4>
            <p className="text-sm text-slate-500 dark:text-slate-400">{solution.approach} approach</p>
          </div>
          <button
            onClick={() => setCodeVisible(!codeVisible)}
            className="text-sm text-primary-600 hover:underline flex items-center gap-1"
          >
            {codeVisible ? 'Hide' : 'Show'} Code
            <ChevronRight className={clsx('w-4 h-4 transition-transform', codeVisible && 'rotate-90')} />
          </button>
        </div>
      </div>
      
      <div className="p-4 space-y-4">
        <div>
          <h5 className="font-medium text-slate-700 dark:text-slate-300 mb-2">Why it works</h5>
          <MarkdownRenderer content={solution.whyItWorks} />
        </div>
        
        {solution.whyNotIdeal && (
          <div>
            <h5 className="font-medium text-slate-700 dark:text-slate-300 mb-2">Why it's not ideal</h5>
            <MarkdownRenderer content={solution.whyNotIdeal} />
          </div>
        )}
        
        {solution.bottleneckRemaining && (
          <div>
            <h5 className="font-medium text-slate-700 dark:text-slate-300 mb-2">Remaining bottleneck</h5>
            <MarkdownRenderer content={solution.bottleneckRemaining} />
          </div>
        )}
        
        {solution.insightToNext && (
          <div className="p-3 bg-amber-50 dark:bg-amber-900/20 border border-amber-200 dark:border-amber-800 rounded-lg">
            <h5 className="font-medium text-amber-800 dark:text-amber-200 mb-1 flex items-center gap-1">
              <Lightbulb className="w-4 h-4" /> Key Insight
            </h5>
            <MarkdownRenderer content={solution.insightToNext} />
          </div>
        )}
        
        <div className="grid grid-cols-2 gap-4 text-sm">
          <div className="p-3 bg-slate-50 dark:bg-slate-800/50 rounded-lg">
            <p className="font-medium text-slate-700 dark:text-slate-300">Time Complexity</p>
            <p className="font-mono text-primary-600 dark:text-primary-400">{solution.complexity.timeComplexity}</p>
            <p className="text-slate-500 dark:text-slate-400 mt-1">{solution.complexity.timeReasoning}</p>
          </div>
          <div className="p-3 bg-slate-50 dark:bg-slate-800/50 rounded-lg">
            <p className="font-medium text-slate-700 dark:text-slate-300">Space Complexity</p>
            <p className="font-mono text-primary-600 dark:text-primary-400">{solution.complexity.spaceComplexity}</p>
            <p className="text-slate-500 dark:text-slate-400 mt-1">{solution.complexity.spaceReasoning}</p>
          </div>
        </div>
        
        {codeVisible && (
          <div>
            <h5 className="font-medium text-slate-700 dark:text-slate-300 mb-2">Implementation</h5>
            <pre className="bg-slate-900 dark:bg-slate-950 rounded-lg p-4 overflow-x-auto text-sm">
              <code className="text-slate-100">{solution.code}</code>
            </pre>
          </div>
        )}
      </div>
    </div>
  );
}

function ProblemTestCases({ problem }: { problem: ProblemChapterDto }) {
  return (
    <div className="p-6 space-y-4">
      <h2 className="text-lg font-semibold text-slate-900 dark:text-slate-100">Test Cases</h2>
      <div className="space-y-2">
        {problem.testCases.map((tc) => (
          <TestCaseCard key={tc.id} testCase={tc} />
        ))}
      </div>
    </div>
  );
}

function TestCaseCard({ testCase }: { testCase: TestCaseDto }) {
  const [expanded, setExpanded] = useState(false);
  
  return (
    <div className="border border-slate-200 dark:border-slate-700 rounded-lg overflow-hidden">
      <button
        onClick={() => setExpanded(!expanded)}
        className="w-full flex items-center justify-between p-3 bg-slate-50 dark:bg-slate-800/50 hover:bg-slate-100 dark:hover:bg-slate-800 transition-colors text-left"
      >
        <div className="flex items-center gap-3">
          <span className={clsx(
            'px-2 py-0.5 rounded text-xs font-medium',
            testCase.type === 'BASIC' && 'bg-blue-100 text-blue-800 dark:bg-blue-900 dark:text-blue-200',
            testCase.type === 'EDGE_CASE' && 'bg-amber-100 text-amber-800 dark:bg-amber-900 dark:text-amber-200',
            testCase.type === 'PERFORMANCE' && 'bg-red-100 text-red-800 dark:bg-red-900 dark:text-red-200',
            testCase.type === 'CUSTOM' && 'bg-slate-100 text-slate-800 dark:bg-slate-700 dark:text-slate-300',
          )}>
            {testCase.type}
          </span>
          <span className="font-medium text-slate-900 dark:text-slate-100">{testCase.name}</span>
          {testCase.isHidden && (
            <span className="px-2 py-0.5 rounded text-xs bg-slate-100 text-slate-600 dark:bg-slate-700 dark:text-slate-400">Hidden</span>
          )}
        </div>
        <ChevronRight className={clsx('w-4 h-4 text-slate-400 transition-transform', expanded && 'rotate-90')} />
      </button>
      {expanded && (
        <div className="p-4 space-y-4 border-t border-slate-200 dark:border-slate-700 bg-slate-50 dark:bg-slate-800/50">
          {testCase.description && (
            <p className="text-sm text-slate-600 dark:text-slate-400">{testCase.description}</p>
          )}
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div>
              <p className="text-xs font-medium text-slate-500 dark:text-slate-400 mb-1">Input</p>
              <pre className="bg-slate-900 dark:bg-slate-950 rounded p-3 text-sm overflow-x-auto text-slate-100"><code>{JSON.stringify(testCase.input, null, 2)}</code></pre>
            </div>
            <div>
              <p className="text-xs font-medium text-slate-500 dark:text-slate-400 mb-1">Expected Output</p>
              <pre className="bg-slate-900 dark:bg-slate-950 rounded p-3 text-sm overflow-x-auto text-slate-100"><code>{JSON.stringify(testCase.expectedOutput, null, 2)}</code></pre>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}