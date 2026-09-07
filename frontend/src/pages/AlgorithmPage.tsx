import { useState } from 'react';
import { useParams } from 'react-router-dom';
import { Link } from 'react-router-dom';
import { Brain, Database, FileCode, ChevronRight, ExternalLink, BookOpen, Lightbulb, Code2, Zap } from 'lucide-react';
import { useAlgorithm } from '@/hooks/useQueries';
import { MarkdownRenderer } from '@/components/ui/MarkdownRenderer';
import clsx from 'clsx';
import type { AlgorithmDetailDto } from '@/types';

const TABS = [
  { id: 'overview', label: 'Overview', icon: BookOpen },
  { id: 'reasoning', label: 'Step-by-Step', icon: Lightbulb },
  { id: 'templates', label: 'Templates', icon: Code2 },
  { id: 'problems', label: 'Problems', icon: FileCode },
  { id: 'references', label: 'References', icon: ExternalLink },
] as const;

export function AlgorithmPage() {
  const { slug } = useParams<{ slug: string }>();
  const { data: algorithm, isLoading, error } = useAlgorithm(slug!);
  const [activeTab, setActiveTab] = useState('overview');
  
  if (isLoading) return <div className="flex items-center justify-center h-64"><div className="animate-spin rounded-full h-8 w-8 border-b-2 border-primary-600" /></div>;
  if (error || !algorithm) return <div className="text-center py-12"><h2 className="text-xl font-semibold">Algorithm not found</h2></div>;
  
  return (
    <div className="space-y-6">
      <div className="flex items-start gap-4">
        <div className="p-3 bg-blue-100 dark:bg-blue-900/30 rounded-xl">
          <Brain className="w-8 h-8 text-blue-600 dark:text-blue-400" />
        </div>
        <div>
          <h1 className="text-2xl font-bold text-slate-900 dark:text-slate-100">{algorithm.name}</h1>
        </div>
      </div>
      
      <div className="border-b border-slate-200 dark:border-slate-700">
        <nav className="flex overflow-x-auto">
          {TABS.map((tab) => (
            <button key={tab.id} onClick={() => setActiveTab(tab.id)} className={clsx('tab whitespace-nowrap flex items-center gap-2', activeTab === tab.id && 'tab-active')}>
              <tab.icon className="w-4 h-4" /> {tab.label}
            </button>
          ))}
        </nav>
      </div>
      
      <div className="card overflow-hidden">
        {activeTab === 'overview' && <AlgorithmOverview algorithm={algorithm} />}
        {activeTab === 'reasoning' && <AlgorithmReasoning algorithm={algorithm} />}
        {activeTab === 'templates' && <AlgorithmTemplates algorithm={algorithm} />}
        {activeTab === 'problems' && <AlgorithmProblems algorithm={algorithm} />}
        {activeTab === 'references' && <AlgorithmReferences algorithm={algorithm} />}
      </div>
    </div>
  );
}

function AlgorithmOverview({ algorithm }: { algorithm: AlgorithmDetailDto }) {
  return (
    <div className="p-6 space-y-6 scrollbar-thin max-h-[calc(100vh-280px)] overflow-y-auto">
      <CollapsibleSection title="Description" defaultExpanded>
        <MarkdownRenderer content={algorithm.description} />
      </CollapsibleSection>
      
      <CollapsibleSection title="Complexity Analysis" defaultExpanded>
        <div className="grid grid-cols-2 gap-4">
          <div className="p-3 bg-slate-50 dark:bg-slate-800/50 rounded-lg">
            <p className="text-sm font-medium text-slate-700 dark:text-slate-300">Time</p>
            <p className="font-mono text-primary-600 dark:text-primary-400">{algorithm.complexity.timeComplexity}</p>
            <p className="text-sm text-slate-500 dark:text-slate-400 mt-1">{algorithm.complexity.timeReasoning}</p>
          </div>
          <div className="p-3 bg-slate-50 dark:bg-slate-800/50 rounded-lg">
            <p className="text-sm font-medium text-slate-700 dark:text-slate-300">Space</p>
            <p className="font-mono text-primary-600 dark:text-primary-400">{algorithm.complexity.spaceComplexity}</p>
            <p className="text-sm text-slate-500 dark:text-slate-400 mt-1">{algorithm.complexity.spaceReasoning}</p>
          </div>
        </div>
      </CollapsibleSection>
      
      <CollapsibleSection title="Complexity Analysis Detail">
        <MarkdownRenderer content={algorithm.complexityAnalysis} />
      </CollapsibleSection>
    </div>
  );
}

function AlgorithmReasoning({ algorithm }: { algorithm: AlgorithmDetailDto }) {
  return (
    <div className="p-6 space-y-6 scrollbar-thin max-h-[calc(100vh-280px)] overflow-y-auto">
      <CollapsibleSection title="Step-by-Step Reasoning" defaultExpanded>
        <MarkdownRenderer content={algorithm.stepByStepReasoning} />
      </CollapsibleSection>
    </div>
  );
}

function AlgorithmTemplates({ algorithm }: { algorithm: AlgorithmDetailDto }) {
  return (
    <div className="p-6 space-y-4">
      {algorithm.templates.length === 0 ? (
        <div className="text-center py-12 text-slate-500">No templates available.</div>
      ) : (
        <div className="space-y-4">
          {algorithm.templates.map((template) => (
            <Link key={template.id} to={`/template/${template.slug}`} className="flex items-center gap-4 p-4 bg-slate-50 dark:bg-slate-800/50 rounded-lg hover:bg-slate-100 dark:hover:bg-slate-800 transition-colors">
              <div className="p-3 bg-primary-100 dark:bg-primary-900/30 rounded-lg"><Code2 className="w-6 h-6 text-primary-600 dark:text-primary-400" /></div>
              <div className="flex-1">
                <h4 className="font-semibold text-slate-900 dark:text-slate-100">{template.name}</h4>
                <p className="text-sm text-slate-500 dark:text-slate-400">{template.language}</p>
              </div>
              <ChevronRight className="w-5 h-5 text-slate-400" />
            </Link>
          ))}
        </div>
      )}
    </div>
  );
}

function AlgorithmProblems({ algorithm }: { algorithm: AlgorithmDetailDto }) {
  return (
    <div className="p-6 space-y-4">
      {algorithm.problems.length === 0 ? (
        <div className="text-center py-12 text-slate-500">No problems linked.</div>
      ) : (
        <div className="space-y-2">
          {algorithm.problems.map((problem) => (
            <Link key={problem.id} to={`/problem/${problem.slug}`} className="flex items-center justify-between p-3 bg-slate-50 dark:bg-slate-800/50 rounded-lg hover:bg-slate-100 dark:hover:bg-slate-800 transition-colors">
              <div className="flex items-center gap-3">
                <FileCode className="w-5 h-5 text-slate-400" />
                <div>
                  <p className="font-medium text-slate-900 dark:text-slate-100">{problem.title}</p>
                  <p className="text-xs text-slate-500">{problem.difficulty}</p>
                </div>
              </div>
              <ChevronRight className="w-4 h-4 text-slate-400" />
            </Link>
          ))}
        </div>
      )}
    </div>
  );
}

function AlgorithmReferences({ algorithm }: { algorithm: AlgorithmDetailDto }) {
  return (
    <div className="p-6 space-y-4">
      {algorithm.references.length === 0 ? (
        <div className="text-center py-12 text-slate-500">No references.</div>
      ) : (
        <ul className="space-y-3">
          {algorithm.references.map((ref) => (
            <li key={ref.id} className="p-3 bg-slate-50 dark:bg-slate-800/50 rounded-lg">
              <a href={ref.url} target="_blank" rel="noopener noreferrer" className="flex items-start gap-3 text-primary-600 hover:underline">
                <ExternalLink className="w-5 h-5 flex-shrink-0 mt-0.5" />
                <div>
                  <p className="font-medium text-slate-900 dark:text-slate-100">{ref.title}</p>
                  <p className="text-sm text-slate-500">{ref.type} • {ref.relevantConcept}</p>
                  {ref.verifies && <p className="text-sm text-slate-500 mt-1">Verifies: {ref.verifies}</p>}
                </div>
              </a>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}

function CollapsibleSection({ title, defaultExpanded = false, children }: { title: string; defaultExpanded?: boolean; children: React.ReactNode }) {
  const [expanded, setExpanded] = useState(defaultExpanded);
  return (
    <div className="border border-slate-200 dark:border-slate-700 rounded-lg overflow-hidden">
      <button onClick={() => setExpanded(!expanded)} className="w-full flex items-center justify-between p-4 bg-slate-50 dark:bg-slate-800/50 hover:bg-slate-100 dark:hover:bg-slate-800 transition-colors text-left">
        <h3 className="font-semibold text-slate-900 dark:text-slate-100">{title}</h3>
        <ChevronRight className={clsx('w-5 h-5 text-slate-400 transition-transform', expanded && 'rotate-90')} />
      </button>
      {expanded && <div className="p-4">{children}</div>}
    </div>
  );
}