import { useState } from 'react';
import { useParams } from 'react-router-dom';
import { Link } from 'react-router-dom';
import { Zap, Brain, Database, FileCode, ChevronRight, ExternalLink, BookOpen, Lightbulb, Code2 } from 'lucide-react';
import { usePattern } from '@/hooks/useQueries';
import { MarkdownRenderer } from '@/components/ui/MarkdownRenderer';
import clsx from 'clsx';
import type { PatternDetailDto } from '@/types';

const TABS = [
  { id: 'overview', label: 'Overview', icon: BookOpen },
  { id: 'why-it-works', label: 'Why It Works', icon: Lightbulb },
  { id: 'templates', label: 'Templates', icon: Code2 },
  { id: 'problems', label: 'Problems', icon: FileCode },
  { id: 'references', label: 'References', icon: ExternalLink },
] as const;

export function PatternPage() {
  const { slug } = useParams<{ slug: string }>();
  const { data: pattern, isLoading, error } = usePattern(slug!);
  const [activeTab, setActiveTab] = useState('overview');
  
  if (isLoading) {
    return <div className="flex items-center justify-center h-64"><div className="animate-spin rounded-full h-8 w-8 border-b-2 border-primary-600" /></div>;
  }
  
  if (error || !pattern) {
    return <div className="text-center py-12"><h2 className="text-xl font-semibold">Pattern not found</h2></div>;
  }
  
  return (
    <div className="space-y-6">
      {/* Header */}
      <div className="flex flex-col sm:flex-row sm:items-start sm:justify-between gap-4">
        <div className="flex items-start gap-4">
          <div className="p-3 bg-purple-100 dark:bg-purple-900/30 rounded-xl">
            <Zap className="w-8 h-8 text-purple-600 dark:text-purple-400" />
          </div>
          <div>
            <h1 className="text-2xl font-bold text-slate-900 dark:text-slate-100">{pattern.name}</h1>
            <p className="text-slate-500 dark:text-slate-400 text-sm mt-1">{pattern.category}</p>
          </div>
        </div>
      </div>
      
      {/* Tabs */}
      <div className="border-b border-slate-200 dark:border-slate-700">
        <nav className="flex overflow-x-auto" aria-label="Pattern sections">
          {TABS.map((tab) => (
            <button
              key={tab.id}
              onClick={() => setActiveTab(tab.id)}
              className={clsx('tab whitespace-nowrap flex items-center gap-2', activeTab === tab.id && 'tab-active')}
            >
              <tab.icon className="w-4 h-4" />
              {tab.label}
            </button>
          ))}
        </nav>
      </div>
      
      {/* Content */}
      <div className="card overflow-hidden">
        {activeTab === 'overview' && <PatternOverview pattern={pattern} />}
        {activeTab === 'why-it-works' && <PatternWhyItWorks pattern={pattern} />}
        {activeTab === 'templates' && <PatternTemplates pattern={pattern} />}
        {activeTab === 'problems' && <PatternProblems pattern={pattern} />}
        {activeTab === 'references' && <PatternReferences pattern={pattern} />}
      </div>
    </div>
  );
}

function PatternOverview({ pattern }: { pattern: PatternDetailDto }) {
  return (
    <div className="p-6 space-y-6 scrollbar-thin max-h-[calc(100vh-280px)] overflow-y-auto">
      <CollapsibleSection title="Definition" defaultExpanded>
        <MarkdownRenderer content={pattern.definition} />
      </CollapsibleSection>
      
      <CollapsibleSection title="When to Use" defaultExpanded>
        <MarkdownRenderer content={pattern.whenToUse} />
      </CollapsibleSection>
      
      <CollapsibleSection title="Recognition Signals" defaultExpanded>
        <div className="flex flex-wrap gap-2">
          {pattern.recognitionSignals.map((signal, i) => (
            <span key={i} className="px-3 py-1 text-sm bg-purple-100 dark:bg-purple-900 text-purple-700 dark:text-purple-300 rounded-full">
              {signal}
            </span>
          ))}
        </div>
      </CollapsibleSection>
      
      <CollapsibleSection title="Complexity" defaultExpanded>
        <div className="grid grid-cols-2 gap-4">
          <div className="p-3 bg-slate-50 dark:bg-slate-800/50 rounded-lg">
            <p className="text-sm font-medium text-slate-700 dark:text-slate-300">Time</p>
            <p className="font-mono text-primary-600 dark:text-primary-400">{pattern.complexity.timeComplexity}</p>
            <p className="text-sm text-slate-500 dark:text-slate-400 mt-1">{pattern.complexity.timeReasoning}</p>
          </div>
          <div className="p-3 bg-slate-50 dark:bg-slate-800/50 rounded-lg">
            <p className="text-sm font-medium text-slate-700 dark:text-slate-300">Space</p>
            <p className="font-mono text-primary-600 dark:text-primary-400">{pattern.complexity.spaceComplexity}</p>
            <p className="text-sm text-slate-500 dark:text-slate-400 mt-1">{pattern.complexity.spaceReasoning}</p>
          </div>
        </div>
      </CollapsibleSection>
      
      <CollapsibleSection title="Strengths & Weaknesses">
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <h4 className="font-medium text-green-700 dark:text-green-300 mb-2">Strengths</h4>
            <MarkdownRenderer content={pattern.strengths || 'Not specified'} />
          </div>
          <div>
            <h4 className="font-medium text-red-700 dark:text-red-300 mb-2">Weaknesses</h4>
            <MarkdownRenderer content={pattern.weaknesses || 'Not specified'} />
          </div>
        </div>
      </CollapsibleSection>
      
      <CollapsibleSection title="Common Failure Modes">
        <MarkdownRenderer content={pattern.commonFailureModes || 'Not specified'} />
      </CollapsibleSection>
      
      <CollapsibleSection title="Generalizations">
        <MarkdownRenderer content={pattern.generalizations || 'Not specified'} />
      </CollapsibleSection>
    </div>
  );
}

function PatternWhyItWorks({ pattern }: { pattern: PatternDetailDto }) {
  return (
    <div className="p-6 space-y-6 scrollbar-thin max-h-[calc(100vh-280px)] overflow-y-auto">
      <CollapsibleSection title="Intuition" defaultExpanded>
        <MarkdownRenderer content={pattern.intuition || 'Not specified'} />
      </CollapsibleSection>
      
      <CollapsibleSection title="Why It Works" defaultExpanded>
        <MarkdownRenderer content={pattern.whyItWorks} />
      </CollapsibleSection>
      
      {pattern.invariant && (
        <CollapsibleSection title="Invariant" defaultExpanded>
          <div className="p-4 bg-amber-50 dark:bg-amber-900/20 border border-amber-200 dark:border-amber-800 rounded-lg">
            <MarkdownRenderer content={pattern.invariant} />
          </div>
        </CollapsibleSection>
      )}
      
      <CollapsibleSection title="Step-by-Step Reasoning">
        <MarkdownRenderer content={pattern.whyItWorks} />
      </CollapsibleSection>
    </div>
  );
}

function PatternTemplates({ pattern }: { pattern: PatternDetailDto }) {
  return (
    <div className="p-6 space-y-6">
      {pattern.templates.length === 0 ? (
        <div className="text-center py-12 text-slate-500 dark:text-slate-400">
          No templates available for this pattern yet.
        </div>
      ) : (
        <div className="space-y-4">
          {pattern.templates.map((template) => (
            <Link key={template.id} to={`/template/${template.slug}`} className="flex items-center gap-4 p-4 bg-slate-50 dark:bg-slate-800/50 rounded-lg hover:bg-slate-100 dark:hover:bg-slate-800 transition-colors">
              <div className="p-3 bg-primary-100 dark:bg-primary-900/30 rounded-lg">
                <Code2 className="w-6 h-6 text-primary-600 dark:text-primary-400" />
              </div>
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

function PatternProblems({ pattern }: { pattern: PatternDetailDto }) {
  return (
    <div className="p-6 space-y-4">
      {pattern.problems.length === 0 ? (
        <div className="text-center py-12 text-slate-500 dark:text-slate-400">
          No problems linked to this pattern yet.
        </div>
      ) : (
        <div className="space-y-2">
          {pattern.problems.map((problem) => (
            <Link key={problem.id} to={`/problem/${problem.slug}`} className="flex items-center justify-between p-3 bg-slate-50 dark:bg-slate-800/50 rounded-lg hover:bg-slate-100 dark:hover:bg-slate-800 transition-colors">
              <div className="flex items-center gap-3">
                <FileCode className="w-5 h-5 text-slate-400" />
                <div>
                  <p className="font-medium text-slate-900 dark:text-slate-100">{problem.title}</p>
                  <p className="text-xs text-slate-500 dark:text-slate-400">{problem.difficulty}</p>
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

function PatternReferences({ pattern }: { pattern: PatternDetailDto }) {
  return (
    <div className="p-6 space-y-4">
      {pattern.references.length === 0 ? (
        <div className="text-center py-12 text-slate-500 dark:text-slate-400">
          No references available.
        </div>
      ) : (
        <ul className="space-y-3">
          {pattern.references.map((ref) => (
            <li key={ref.id} className="p-3 bg-slate-50 dark:bg-slate-800/50 rounded-lg">
              <a href={ref.url} target="_blank" rel="noopener noreferrer" className="flex items-start gap-3 text-primary-600 hover:underline">
                <ExternalLink className="w-5 h-5 flex-shrink-0 mt-0.5" />
                <div>
                  <p className="font-medium text-slate-900 dark:text-slate-100">{ref.title}</p>
                  <p className="text-sm text-slate-500 dark:text-slate-400">{ref.type} • {ref.relevantConcept}</p>
                  {ref.verifies && <p className="text-sm text-slate-500 dark:text-slate-400 mt-1">Verifies: {ref.verifies}</p>}
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
      <button
        onClick={() => setExpanded(!expanded)}
        className="w-full flex items-center justify-between p-4 bg-slate-50 dark:bg-slate-800/50 hover:bg-slate-100 dark:hover:bg-slate-800 transition-colors text-left"
      >
        <h3 className="font-semibold text-slate-900 dark:text-slate-100">{title}</h3>
        <ChevronRight className={clsx('w-5 h-5 text-slate-400 transition-transform', expanded && 'rotate-90')} />
      </button>
      {expanded && <div className="p-4">{children}</div>}
    </div>
  );
}