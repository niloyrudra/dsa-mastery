import { useEffect, useState } from 'react';
import Editor from '@monaco-editor/react';
import { Play, RotateCcw, Copy, Terminal, Zap, Database } from 'lucide-react';
import clsx from 'clsx';
import type { ProblemChapterDto, ExecutionResultDto } from '@/types';
import { TestResults } from '@/components/problem/TestResults';

interface CodeEditorProps {
  problem: ProblemChapterDto;
  language: 'TYPESCRIPT' | 'JAVASCRIPT' | 'PYTHON';
  code: string;
  onLanguageChange: (lang: 'TYPESCRIPT' | 'JAVASCRIPT' | 'PYTHON') => void;
  onCodeChange: (code: string) => void;
  onRun: (code: string) => void;
  isExecuting: boolean;
  executionJobId: string | null;
  executionResult: ExecutionResultDto | null;
}

const LANGUAGE_OPTIONS = [
  { value: 'TYPESCRIPT', label: 'TypeScript', monaco: 'typescript' },
  { value: 'JAVASCRIPT', label: 'JavaScript', monaco: 'javascript' },
  { value: 'PYTHON', label: 'Python', monaco: 'python' },
] as const;

const DEFAULT_CODE = {
  TYPESCRIPT: `function solution(nums: number[], target: number): number[] {
  // Your solution here
}`,
  JAVASCRIPT: `function solution(nums, target) {
  // Your solution here
}`,
  PYTHON: `def solution(nums, target):
    # Your solution here
    pass`,
};

export function CodeEditor({ 
  problem, 
  language, 
  code, 
  onLanguageChange, 
  onCodeChange, 
  onRun,
  isExecuting,
  executionJobId,
  executionResult
}: CodeEditorProps) {
  const [showTestResults, setShowTestResults] = useState(false);
  const executionTime = executionResult?.executionTimeMs ?? null;
  const memoryUsed = executionResult?.memoryBytes ?? null;
  
  const handleEditorChange = (value: string | undefined) => {
    if (value !== undefined) {
      onCodeChange(value);
    }
  };
  
  const handleRun = () => {
    if (code.trim()) {
      onRun(code);
      setShowTestResults(true);
    }
  };
  
  const handleReset = () => {
    onCodeChange(DEFAULT_CODE[language]);
  };
  
  const handleCopy = async () => {
    await navigator.clipboard.writeText(code);
  };
  
  const languageOption = LANGUAGE_OPTIONS.find(l => l.value === language);
  
  return (
    <div className="h-[calc(100vh-280px)] flex flex-col overflow-hidden">
      {/* Toolbar */}
      <div className="flex items-center justify-between p-3 bg-slate-50 dark:bg-slate-800/50 border-b border-slate-200 dark:border-slate-700">
        <div className="flex items-center gap-2">
          <select
            value={language}
            onChange={(e) => onLanguageChange(e.target.value as any)}
            className="px-3 py-1.5 text-sm bg-white dark:bg-slate-800 border border-slate-300 dark:border-slate-600 rounded-lg focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-primary-500"
          >
            {LANGUAGE_OPTIONS.map(opt => (
              <option key={opt.value} value={opt.value}>{opt.label}</option>
            ))}
          </select>
          
          <div className="w-px h-6 bg-slate-300 dark:bg-slate-600 mx-2" />
          
          <button
            onClick={handleRun}
            disabled={isExecuting || !code.trim()}
            className={clsx(
              'btn-primary flex items-center gap-2',
              isExecuting && 'opacity-70 cursor-wait',
              !code.trim() && 'opacity-50 cursor-not-allowed'
            )}
          >
            {isExecuting ? (
              <>
                <div className="w-4 h-4 border-2 border-white/30 border-t-white rounded-full animate-spin" />
                Running...
              </>
            ) : (
              <>
                <Play className="w-4 h-4" />
                Run Tests
              </>
            )}
          </button>
          
          <button onClick={handleReset} className="btn-secondary text-sm flex items-center gap-2" title="Reset to template">
            <RotateCcw className="w-4 h-4" /> Reset
          </button>
          
          <button onClick={handleCopy} className="btn-secondary text-sm flex items-center gap-2" title="Copy code">
            <Copy className="w-4 h-4" />
          </button>
        </div>
        
        <div className="flex items-center gap-4">
          {executionTime !== null && (
            <span className="flex items-center gap-1 text-sm text-slate-500 dark:text-slate-400">
              <Zap className="w-4 h-4" />
              {executionTime}ms
            </span>
          )}
          {memoryUsed !== null && (
            <span className="flex items-center gap-1 text-sm text-slate-500 dark:text-slate-400">
              <Database className="w-4 h-4" />
              {(memoryUsed / 1024 / 1024).toFixed(1)} MB
            </span>
          )}
          <button
            onClick={() => setShowTestResults(!showTestResults)}
            className={clsx(
              'btn-secondary text-sm flex items-center gap-2',
              showTestResults && 'bg-primary-50 dark:bg-primary-900/30 text-primary-700 dark:text-primary-300 border-primary-500'
            )}
          >
            <Terminal className="w-4 h-4" />
            {showTestResults ? 'Hide' : 'Show'} Results
          </button>
        </div>
      </div>
      
      {/* Editor */}
      <div className="flex-1 relative overflow-hidden">
        <Editor
          height="100%"
          language={languageOption?.monaco || 'typescript'}
          theme="vs-dark"
          value={code}
          onChange={handleEditorChange}
          options={{
            minimap: { enabled: false },
            fontSize: 14,
            lineNumbers: 'on',
            scrollBeyondLastLine: false,
            automaticLayout: true,
            tabSize: 2,
            insertSpaces: true,
            wordWrap: 'on',
            bracketPairColorization: { enabled: true },
            guides: { bracketPairs: true },
            renderLineHighlight: 'all',
            scrollbar: { verticalScrollbarSize: 8, horizontalScrollbarSize: 8 },
          }}
        />
      </div>
      
      {/* Test Results Panel */}
      {showTestResults && (
        <div className="border-t border-slate-200 dark:border-slate-700 bg-slate-50 dark:bg-slate-800/50 max-h-72 overflow-y-auto">
          <div className="p-3 flex items-center justify-between bg-slate-100 dark:bg-slate-800 border-b border-slate-200 dark:border-slate-700">
            <h3 className="font-medium text-slate-900 dark:text-slate-100">Test Results</h3>
            <span className="text-sm text-slate-500 dark:text-slate-400">
              {executionResult ? `${executionResult.passedTests} / ${executionResult.totalTests} passed` : 'Awaiting run…'}
            </span>
          </div>
          <TestResults result={executionResult} isLoading={isExecuting} />
        </div>
      )}
    </div>
  );
}