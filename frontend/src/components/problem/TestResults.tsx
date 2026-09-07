import { CheckCircle, XCircle, Clock, Database, AlertCircle, Loader2 } from 'lucide-react';
import clsx from 'clsx';
import type { ExecutionResultDto, TestResultDto } from '@/types';

interface TestResultsProps {
  result: ExecutionResultDto | null;
  isLoading?: boolean;
}

const statusConfig = {
  PASSED: { icon: CheckCircle, color: 'text-green-500', bg: 'bg-green-100 dark:bg-green-900/30', label: 'Passed' },
  FAILED: { icon: XCircle, color: 'text-red-500', bg: 'bg-red-100 dark:bg-red-900/30', label: 'Failed' },
  ERROR: { icon: AlertCircle, color: 'text-orange-500', bg: 'bg-orange-100 dark:bg-orange-900/30', label: 'Error' },
  TIMEOUT: { icon: Clock, color: 'text-yellow-500', bg: 'bg-yellow-100 dark:bg-yellow-900/30', label: 'Timeout' },
  RUNNING: { icon: Loader2, color: 'text-blue-500', bg: 'bg-blue-100 dark:bg-blue-900/30', label: 'Running' },
  COMPILING: { icon: Loader2, color: 'text-purple-500', bg: 'bg-purple-100 dark:bg-purple-900/30', label: 'Compiling' },
  QUEUED: { icon: Clock, color: 'text-slate-500', bg: 'bg-slate-100 dark:bg-slate-800', label: 'Queued' },
} as const;

export function TestResults({ result, isLoading }: TestResultsProps) {
  if (!result && !isLoading) return null;
  
  const status = result?.status || 'QUEUED';
  const config = statusConfig[status as keyof typeof statusConfig] || statusConfig.QUEUED;
  const Icon = config.icon;
  
  return (
    <div className="card overflow-hidden">
      <div className={clsx('p-4 border-b border-slate-200 dark:border-slate-700 flex items-center justify-between', config.bg)}>
        <div className="flex items-center gap-3">
          <Icon className={clsx('w-6 h-6', config.color)} />
          <div>
            <h3 className="font-semibold text-slate-900 dark:text-slate-100">
              {config.label} {result && `(${result.passedTests}/${result.totalTests})`}
            </h3>
            {result && (
              <p className="text-sm text-slate-500 dark:text-slate-400">
                {result.executionTimeMs ? `${result.executionTimeMs}ms` : ''} 
                {result.memoryBytes ? ` • ${(result.memoryBytes / 1024 / 1024).toFixed(1)} MB` : ''}
              </p>
            )}
          </div>
        </div>
        
        {result && (
          <div className="flex items-center gap-2 text-sm text-slate-500 dark:text-slate-400">
            <span className="px-2 py-0.5 rounded bg-slate-100 dark:bg-slate-800 font-mono">
              {result.status}
            </span>
            {result.completedAt && (
              <span>Completed {new Date(result.completedAt).toLocaleTimeString()}</span>
            )}
          </div>
        )}
      </div>
      
      {isLoading && (
        <div className="p-4 text-center">
          <Loader2 className="w-8 h-8 animate-spin mx-auto text-primary-600 mb-2" />
          <p className="text-slate-500 dark:text-slate-400">Running tests...</p>
        </div>
      )}
      
{result && result.testResults.length > 0 && (
        <div className="divide-y divide-slate-200 dark:divide-slate-700">
          {result.testResults.map((test, index) => (
            <TestCaseRow key={index} test={test} index={index} />
          ))}
        </div>
      )}

      {result && result.error && (
        <div className="p-4 bg-red-50 dark:bg-red-900/20 border-t border-red-200 dark:border-red-800">
          <h4 className="font-medium text-red-800 dark:text-red-200 mb-2 flex items-center gap-2">
            <AlertCircle className="w-4 h-4" /> Error
          </h4>
          <pre className="text-sm text-red-700 dark:text-red-300 bg-red-100 dark:bg-red-900/30 p-3 rounded overflow-x-auto">
            {result.error}
          </pre>
        </div>
      )}
      
      {result && result.output && !result.error && (
        <details className="p-4 border-t border-slate-200 dark:border-slate-700">
          <summary className="font-medium text-slate-700 dark:text-slate-300 cursor-pointer mb-2">
            Full Output
          </summary>
          <pre className="text-sm text-slate-700 dark:text-slate-300 bg-slate-100 dark:bg-slate-800 p-3 rounded overflow-x-auto max-h-48 overflow-y-auto">
            {result.output}
          </pre>
        </details>
      )}
    </div>
  );
}

function TestCaseRow({ test, index }: { test: TestResultDto; index: number }) {
  const passed = test.passed;
  
  return (
    <div className="p-4 hover:bg-slate-50 dark:hover:bg-slate-800/50 transition-colors">
      <div className="flex items-start justify-between gap-4">
        <div className="flex items-center gap-3 flex-1 min-w-0">
          {passed ? (
            <CheckCircle className="w-5 h-5 text-green-500 flex-shrink-0" />
          ) : (
            <XCircle className="w-5 h-5 text-red-500 flex-shrink-0" />
          )}
          <div className="min-w-0">
            <p className="font-medium text-sm text-slate-900 dark:text-slate-100 truncate">
              {test.name || `Test ${index + 1}`}
            </p>
            {test.durationMs && (
              <p className="text-xs text-slate-500 dark:text-slate-400">{test.durationMs}ms</p>
            )}
          </div>
        </div>
        
        <div className="flex items-center gap-2 flex-shrink-0">
          {passed && <span className="text-xs text-green-600 dark:text-green-400 font-medium">Passed</span>}
          {!passed && <span className="text-xs text-red-600 dark:text-red-400 font-medium">Failed</span>}
        </div>
      </div>
      
      {!passed && (
        <div className="mt-3 space-y-2 text-sm">
          <div>
            <p className="text-xs font-medium text-slate-500 dark:text-slate-400 mb-1">Input</p>
            <pre className="bg-slate-900 dark:bg-slate-950 rounded p-2 text-xs overflow-x-auto text-slate-100 max-h-24 overflow-y-auto">
              {typeof test.input === 'string' ? test.input : JSON.stringify(test.input, null, 2)}
            </pre>
          </div>
          <div className="grid grid-cols-2 gap-2">
            <div>
              <p className="text-xs font-medium text-slate-500 dark:text-slate-400 mb-1">Expected</p>
              <pre className="bg-slate-900 dark:bg-slate-950 rounded p-2 text-xs overflow-x-auto text-slate-100">
                {typeof test.expected === 'string' ? test.expected : JSON.stringify(test.expected, null, 2)}
              </pre>
            </div>
            <div>
              <p className="text-xs font-medium text-slate-500 dark:text-slate-400 mb-1">Actual</p>
              <pre className="bg-slate-900 dark:bg-slate-950 rounded p-2 text-xs overflow-x-auto text-slate-100">
                {typeof test.actual === 'string' ? test.actual : JSON.stringify(test.actual, null, 2)}
              </pre>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}