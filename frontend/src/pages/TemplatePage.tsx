import { useState } from 'react';
import { useParams } from 'react-router-dom';
import { Link } from 'react-router-dom';
import { ChevronRight, ExternalLink, BookOpen, Code2, Zap, FileCode, Copy, Check } from 'lucide-react';
import { useTemplate } from '@/hooks/useQueries';
import { MarkdownRenderer } from '@/components/ui/MarkdownRenderer';
import clsx from 'clsx';
import type { TemplateDetailDto } from '@/types';

const TABS = [
  { id: 'overview', label: 'Overview', icon: BookOpen },
  { id: 'code', label: 'Template Code', icon: Code2 },
  { id: 'mappings', label: 'Mappings', icon: Zap },
  { id: 'problems', label: 'Problems', icon: FileCode },
] as const;

export function TemplatePage() {
  const { slug } = useParams<{ slug: string }>();
  const { data: template, isLoading, error } = useTemplate(slug!);
  const [activeTab, setActiveTab] = useState('overview');
  const [copied, setCopied] = useState<string | null>(null);
  
  if (isLoading) return <div className="flex items-center justify-center h-64"><div className="animate-spin rounded-full h-8 w-8 border-b-2 border-primary-600" /></div>;
  if (error || !template) return <div className="text-center py-12"><h2 className="text-xl font-semibold">Template not found</h2></div>;
  
  const copyToClipboard = (text: string, label: string) => {
    navigator.clipboard.writeText(text);
    setCopied(label);
    setTimeout(() => setCopied(null), 2000);
  };
  
  return (
    <div className="space-y-6">
      <div className="flex flex-col sm:flex-row sm:items-start sm:justify-between gap-4">
        <div className="flex items-start gap-4">
          <div className="p-3 bg-primary-100 dark:bg-primary-900/30 rounded-xl">
            <Code2 className="w-8 h-8 text-primary-600 dark:text-primary-400" />
          </div>
          <div>
            <div className="flex items-center gap-2 mb-1">
              <h1 className="text-2xl font-bold text-slate-900 dark:text-slate-100">{template.name}</h1>
              <span className="px-2 py-0.5 rounded text-xs font-medium bg-slate-100 dark:bg-slate-800 text-slate-600 dark:text-slate-400">
                {template.language}
              </span>
            </div>
            <p className="text-slate-500 dark:text-slate-400 text-sm">{template.pattern?.name}</p>
          </div>
        </div>
        <div className="flex gap-2">
          <button onClick={() => copyToClipboard(template.code, 'code')} className="btn-secondary text-sm flex items-center gap-2">
            <Copy className="w-4 h-4" />
            {copied === 'code' ? <Check className="w-4 h-4" /> : 'Copy'}
          </button>
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
        {activeTab === 'overview' && <TemplateOverview template={template} />}
        {activeTab === 'code' && <TemplateCode template={template} copyToClipboard={copyToClipboard} />}
        {activeTab === 'mappings' && <TemplateMappings template={template} />}
        {activeTab === 'problems' && <TemplateProblems template={template} />}
      </div>
    </div>
  );
}

function TemplateOverview({ template }: { template: TemplateDetailDto }) {
  return (
    <div className="p-6 space-y-6 scrollbar-thin max-h-[calc(100vh-280px)] overflow-y-auto">
      <CollapsibleSection title="Purpose" defaultExpanded>
        <MarkdownRenderer content={template.purpose} />
      </CollapsibleSection>
      
      <CollapsibleSection title="When to Reach For It" defaultExpanded>
        <MarkdownRenderer content={template.whenToReachForIt} />
      </CollapsibleSection>
      
      {template.prerequisites && (
        <CollapsibleSection title="Prerequisites">
          <MarkdownRenderer content={template.prerequisites} />
        </CollapsibleSection>
      )}
      
      <CollapsibleSection title="Mental Model" defaultExpanded>
        <MarkdownRenderer content={template.mentalModel} />
      </CollapsibleSection>
      
      <CollapsibleSection title="Invariant" defaultExpanded>
        <div className="p-4 bg-amber-50 dark:bg-amber-900/20 border border-amber-200 dark:border-amber-800 rounded-lg">
          <MarkdownRenderer content={template.invariant} />
        </div>
      </CollapsibleSection>
      
      <CollapsibleSection title="Complexity" defaultExpanded>
        <div className="grid grid-cols-2 gap-4">
          <div className="p-3 bg-slate-50 dark:bg-slate-800/50 rounded-lg">
            <p className="text-sm font-medium text-slate-700 dark:text-slate-300">Time</p>
            <p className="font-mono text-primary-600 dark:text-primary-400">{template.complexity.timeComplexity}</p>
            <p className="text-sm text-slate-500 dark:text-slate-400 mt-1">{template.complexity.timeReasoning}</p>
          </div>
          <div className="p-3 bg-slate-50 dark:bg-slate-800/50 rounded-lg">
            <p className="text-sm font-medium text-slate-700 dark:text-slate-300">Space</p>
            <p className="font-mono text-primary-600 dark:text-primary-400">{template.complexity.spaceComplexity}</p>
            <p className="text-sm text-slate-500 dark:text-slate-400 mt-1">{template.complexity.spaceReasoning}</p>
          </div>
        </div>
      </CollapsibleSection>
      
      {template.commonModifications && (
        <CollapsibleSection title="Common Modifications">
          <MarkdownRenderer content={template.commonModifications} />
        </CollapsibleSection>
      )}
      
      {template.commonMistakes && (
        <CollapsibleSection title="Common Mistakes">
          <MarkdownRenderer content={template.commonMistakes} />
        </CollapsibleSection>
      )}
    </div>
  );
}

function TemplateCode({ template, copyToClipboard }: { template: TemplateDetailDto; copyToClipboard: (text: string, label: string) => void }) {
  return (
    <div className="p-6 space-y-6">
      <div className="flex items-center justify-between">
        <h2 className="text-lg font-semibold text-slate-900 dark:text-slate-100">Template Code</h2>
        <button onClick={() => copyToClipboard(template.code, 'code')} className="btn-secondary text-sm flex items-center gap-2">
          <Copy className="w-4 h-4" /> Copy
        </button>
      </div>
      
      <div className="bg-slate-900 dark:bg-slate-950 rounded-lg p-4 overflow-x-auto">
        <pre className="text-sm text-slate-100"><code>{template.code}</code></pre>
      </div>
      
      <CollapsibleSection title="Line-by-Line Explanation" defaultExpanded>
        <MarkdownRenderer content={template.explanation} />
      </CollapsibleSection>
    </div>
  );
}

function TemplateMappings({ template }: { template: TemplateDetailDto }) {
  return (
    <div className="p-6 space-y-6">
      {template.mappings.length === 0 ? (
        <div className="text-center py-12 text-slate-500">No template mappings available.</div>
      ) : (
        <div className="space-y-4">
          {template.mappings.map((mapping, i) => (
            <div key={i} className="border border-slate-200 dark:border-slate-700 rounded-lg overflow-hidden">
              <div className="p-4 bg-slate-50 dark:bg-slate-800/50 border-b border-slate-200 dark:border-slate-700">
                <div className="flex items-center justify-between">
                  <h4 className="font-semibold text-slate-900 dark:text-slate-100">Marker [{mapping.templateMarker}]</h4>
                  <span className="px-2 py-0.5 rounded text-xs font-medium bg-primary-100 dark:bg-primary-900 text-primary-700 dark:text-primary-300">
                    Template → Solution
                  </span>
                </div>
              </div>
              <div className="p-4 space-y-4">
                <div>
                  <h5 className="font-medium text-slate-700 dark:text-slate-300 mb-2">Template Context</h5>
                  <pre className="bg-slate-900 dark:bg-slate-950 rounded p-3 text-sm overflow-x-auto text-slate-100"><code>{mapping.solutionCodeSnippet}</code></pre>
                </div>
                <div>
                  <h5 className="font-medium text-slate-700 dark:text-slate-300 mb-2">Explanation</h5>
                  <MarkdownRenderer content={mapping.explanation} />
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

function TemplateProblems({ template }: { template: TemplateDetailDto }) {
  return (
    <div className="p-6 space-y-4">
      {template.exampleProblems.length === 0 ? (
        <div className="text-center py-12 text-slate-500">No example problems.</div>
      ) : (
        <div className="space-y-2">
          {template.exampleProblems.map((problem) => (
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