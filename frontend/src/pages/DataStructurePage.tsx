import { useState } from 'react';
import { useParams } from 'react-router-dom';
import { Link } from 'react-router-dom';
import { Database, FileCode, ChevronRight, ExternalLink, BookOpen, Code2, Zap } from 'lucide-react';
import { useDataStructure } from '@/hooks/useQueries';
import { MarkdownRenderer } from '@/components/ui/MarkdownRenderer';
import clsx from 'clsx';
import type { DataStructureDetailDto } from '@/types';

const TABS = [
  { id: 'overview', label: 'Overview', icon: BookOpen },
  { id: 'operations', label: 'Operations', icon: Zap },
  { id: 'problems', label: 'Problems', icon: FileCode },
  { id: 'patterns', label: 'Patterns', icon: Code2 },
] as const;

export function DataStructurePage() {
  const { slug } = useParams<{ slug: string }>();
  const { data: ds, isLoading, error } = useDataStructure(slug!);
  const [activeTab, setActiveTab] = useState('overview');
  
  if (isLoading) return <div className="flex items-center justify-center h-64"><div className="animate-spin rounded-full h-8 w-8 border-b-2 border-primary-600" /></div>;
  if (error || !ds) return <div className="text-center py-12"><h2 className="text-xl font-semibold">Data Structure not found</h2></div>;
  
  return (
    <div className="space-y-6">
      <div className="flex items-start gap-4">
        <div className="p-3 bg-green-100 dark:bg-green-900/30 rounded-xl">
          <Database className="w-8 h-8 text-green-600 dark:text-green-400" />
        </div>
        <div>
          <h1 className="text-2xl font-bold text-slate-900 dark:text-slate-100">{ds.name}</h1>
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
        {activeTab === 'overview' && <DataStructureOverview ds={ds} />}
        {activeTab === 'operations' && <DataStructureOperations ds={ds} />}
        {activeTab === 'problems' && <DataStructureProblems ds={ds} />}
        {activeTab === 'patterns' && <DataStructurePatterns ds={ds} />}
      </div>
    </div>
  );
}

function DataStructureOverview({ ds }: { ds: DataStructureDetailDto }) {
  return (
    <div className="p-6 space-y-6 scrollbar-thin max-h-[calc(100vh-280px)] overflow-y-auto">
      <CollapsibleSection title="Description" defaultExpanded>
        <MarkdownRenderer content={ds.description} />
      </CollapsibleSection>
      
      {ds.whyUsefulHere && (
        <CollapsibleSection title="Why Useful" defaultExpanded>
          <MarkdownRenderer content={ds.whyUsefulHere} />
        </CollapsibleSection>
      )}
      
      {ds.tradeoffs && (
        <CollapsibleSection title="Trade-offs">
          <MarkdownRenderer content={ds.tradeoffs} />
        </CollapsibleSection>
      )}
      
      {ds.alternatives && (
        <CollapsibleSection title="Alternatives">
          <MarkdownRenderer content={ds.alternatives} />
        </CollapsibleSection>
      )}
    </div>
  );
}

function DataStructureOperations({ ds }: { ds: DataStructureDetailDto }) {
  return (
    <div className="p-6 space-y-6">
      <h2 className="text-lg font-semibold text-slate-900 dark:text-slate-100">Operations & Complexity</h2>
      <div className="overflow-x-auto">
        <table className="w-full text-left">
          <thead>
            <tr className="border-b border-slate-200 dark:border-slate-700">
              <th className="pb-2 font-medium text-slate-700 dark:text-slate-300">Operation</th>
              <th className="pb-2 font-medium text-slate-700 dark:text-slate-300">Complexity</th>
            </tr>
          </thead>
          <tbody>
            {Object.entries(ds.operations).map(([op, complexity]) => (
              <tr key={op} className="border-b border-slate-100 dark:border-slate-800">
                <td className="py-3 font-medium text-slate-900 dark:text-slate-100">{op}</td>
                <td className="py-3 font-mono text-primary-600 dark:text-primary-400">{complexity}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

function DataStructureProblems({ ds }: { ds: DataStructureDetailDto }) {
  return (
    <div className="p-6 space-y-4">
      {ds.problems.length === 0 ? (
        <div className="text-center py-12 text-slate-500">No problems linked.</div>
      ) : (
        <div className="space-y-2">
          {ds.problems.map((problem) => (
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

function DataStructurePatterns({ ds }: { ds: DataStructureDetailDto }) {
  return (
    <div className="p-6 space-y-4">
      {ds.patterns.length === 0 ? (
        <div className="text-center py-12 text-slate-500">No patterns linked.</div>
      ) : (
        <div className="space-y-2">
          {ds.patterns.map((pattern) => (
            <Link key={pattern.id} to={`/pattern/${pattern.slug}`} className="flex items-center justify-between p-3 bg-slate-50 dark:bg-slate-800/50 rounded-lg hover:bg-slate-100 dark:hover:bg-slate-800 transition-colors">
              <div className="flex items-center gap-3">
                <Zap className="w-5 h-5 text-purple-500" />
                <div>
                  <p className="font-medium text-slate-900 dark:text-slate-100">{pattern.name}</p>
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