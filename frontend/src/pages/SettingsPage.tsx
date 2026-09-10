import { useState } from 'react';
import { Moon, Sun, Monitor, Database, Save, Download, Upload, Trash2, AlertCircle, CheckCircle, Code2 } from 'lucide-react';
import { useUIStore, useEditorStore, useMasteryStore, useAITutorStore } from '@/store';
import clsx from 'clsx';

export function SettingsPage() {
  const { theme, setTheme } = useUIStore();
  const { language: editorLanguage } = useEditorStore();
  const { problemMastery } = useMasteryStore();
  const { isOpen: aiOpen } = useAITutorStore();
  const [activeSection, setActiveSection] = useState<'appearance' | 'editor' | 'progress' | 'data'>('appearance');
  const [exportData, setExportData] = useState<string>('');
  const [importMessage, setImportMessage] = useState<{ type: 'success' | 'error'; text: string } | null>(null);
  
  const handleExport = () => {
    const data = {
      theme,
      editorLanguage,
      problemMastery,
      aiConversations: [], // Would be from AI store
      exportedAt: new Date().toISOString(),
    };
    const json = JSON.stringify(data, null, 2);
    setExportData(json);
    
    const blob = new Blob([json], { type: 'application/json' });
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = `dsa-mastery-backup-${new Date().toISOString().split('T')[0]}.json`;
    a.click();
    URL.revokeObjectURL(url);
  };
  
  const handleImport = (file: File) => {
    const reader = new FileReader();
    reader.onload = (e) => {
      try {
        const data = JSON.parse(e.target?.result as string);
        if (data.theme) setTheme(data.theme);
        if (data.editorLanguage) useEditorStore.getState().setLanguage(data.editorLanguage);
        if (data.problemMastery) useMasteryStore.getState().updateProblemMastery('temp', 'NOT_STARTED'); // Would need proper merge
        setImportMessage({ type: 'success', text: 'Settings imported successfully!' });
      } catch {
        setImportMessage({ type: 'error', text: 'Invalid backup file' });
      }
    };
    reader.readAsText(file);
  };
  
  const handleClearAllData = () => {
    if (confirm('This will delete all your progress data. Are you sure?')) {
      localStorage.clear();
      window.location.reload();
    }
  };
  
  return (
    <div className="max-w-3xl mx-auto space-y-8">
      <div>
        <h1 className="text-2xl font-bold text-slate-900 dark:text-slate-100">Settings</h1>
        <p className="text-slate-500 dark:text-slate-400 mt-1">Manage your preferences and data</p>
      </div>
      
      {/* Sidebar Navigation */}
      <div className="flex flex-col lg:flex-row gap-6">
        <nav className="lg:w-48 flex-shrink-0 space-y-1" aria-label="Settings sections">
          {[
            { id: 'appearance', label: 'Appearance', icon: Monitor },
            { id: 'editor', label: 'Editor', icon: Code2 },
            { id: 'progress', label: 'Progress', icon: Database },
            { id: 'data', label: 'Data', icon: Save },
          ].map((section) => (
            <button
              key={section.id}
              onClick={() => setActiveSection(section.id as 'appearance' | 'editor' | 'progress' | 'data')}
              className={clsx(
                'w-full flex items-center gap-3 px-3 py-2 rounded-lg text-sm font-medium transition-colors text-left',
                activeSection === section.id
                  ? 'bg-primary-50 text-primary-700 dark:bg-primary-900/30 dark:text-primary-300'
                  : 'text-slate-600 hover:bg-slate-100 dark:text-slate-400 dark:hover:bg-slate-800'
              )}
            >
              <section.icon className="w-5 h-5 flex-shrink-0" />
              {section.label}
            </button>
          ))}
        </nav>
        
        <div className="flex-1 space-y-6">
          {/* Appearance */}
          {activeSection === 'appearance' && (
            <div className="card p-6 space-y-6">
              <h2 className="text-lg font-semibold text-slate-900 dark:text-slate-100">Theme</h2>
              <p className="text-slate-500 dark:text-slate-400 text-sm">Choose your preferred color scheme</p>
              
              <div className="grid grid-cols-3 gap-3">
                {[
                  { value: 'light', label: 'Light', icon: Sun, desc: 'Always light mode' },
                  { value: 'dark', label: 'Dark', icon: Moon, desc: 'Always dark mode' },
                  { value: 'system', label: 'System', icon: Monitor, desc: 'Match OS setting' },
                ].map((option) => (
                  <button
                    key={option.value}
                    onClick={() => setTheme(option.value as 'light' | 'dark' | 'system')}
                    className={clsx(
                      'p-4 rounded-lg border-2 transition-colors text-left',
                      theme === option.value
                        ? 'border-primary-500 bg-primary-50 dark:bg-primary-900/20'
                        : 'border-slate-200 dark:border-slate-700 hover:border-slate-300 dark:hover:border-slate-600'
                    )}
                  >
                    <option.icon className={clsx('w-6 h-6 mx-auto mb-2', theme === option.value ? 'text-primary-600 dark:text-primary-400' : 'text-slate-400')} />
                    <p className="font-medium text-slate-900 dark:text-slate-100 text-center">{option.label}</p>
                    <p className="text-xs text-slate-500 dark:text-slate-400 text-center mt-1">{option.desc}</p>
                  </button>
                ))}
              </div>
            </div>
          )}
          
          {/* Editor */}
          {activeSection === 'editor' && (
            <div className="card p-6 space-y-6">
              <h2 className="text-lg font-semibold text-slate-900 dark:text-slate-100">Default Language</h2>
              <p className="text-slate-500 dark:text-slate-400 text-sm">Set your preferred programming language for the code editor</p>
              
              <div className="grid grid-cols-3 gap-3">
                {[
                  { value: 'TYPESCRIPT', label: 'TypeScript', icon: Code2 },
                  { value: 'JAVASCRIPT', label: 'JavaScript', icon: Code2 },
                  { value: 'PYTHON', label: 'Python', icon: Code2 },
                ].map((option) => (
                  <button
                    key={option.value}
                    onClick={() => useEditorStore.getState().setLanguage(option.value as any)}
                    className={clsx(
                      'p-4 rounded-lg border-2 transition-colors text-left',
                      editorLanguage === option.value
                        ? 'border-primary-500 bg-primary-50 dark:bg-primary-900/20'
                        : 'border-slate-200 dark:border-slate-700 hover:border-slate-300 dark:hover:border-slate-600'
                    )}
                  >
                    <option.icon className={clsx('w-6 h-6 mx-auto mb-2', editorLanguage === option.value ? 'text-primary-600 dark:text-primary-400' : 'text-slate-400')} />
                    <p className="font-medium text-slate-900 dark:text-slate-100 text-center">{option.label}</p>
                  </button>
                ))}
              </div>
            </div>
          )}
          
          {/* Progress */}
          {activeSection === 'progress' && (
            <div className="card p-6 space-y-6">
              <h2 className="text-lg font-semibold text-slate-900 dark:text-slate-100">Learning Progress</h2>
              <p className="text-slate-500 dark:text-slate-400 text-sm">View and manage your mastery records</p>
              
              <div className="space-y-2 max-h-96 overflow-y-auto">
                {Object.entries(problemMastery).length === 0 ? (
                  <p className="text-center py-8 text-slate-500 dark:text-slate-400">No progress recorded yet. Start solving problems!</p>
                ) : (
                  Object.entries(problemMastery).map(([problemId, status]) => (
                    <div key={problemId} className="flex items-center justify-between p-3 bg-slate-50 dark:bg-slate-800/50 rounded-lg">
                      <span className="font-mono text-sm text-slate-700 dark:text-slate-300">{problemId.slice(0, 8)}...</span>
                      <span className={clsx(
                        'px-2 py-0.5 rounded text-xs font-medium',
                        status === 'MASTERED' && 'bg-emerald-100 text-emerald-800 dark:bg-emerald-900 dark:text-emerald-200',
                        status === 'SOLVED' && 'bg-blue-100 text-blue-800 dark:bg-blue-900 dark:text-blue-200',
                        status === 'PRACTICING' && 'bg-amber-100 text-amber-800 dark:bg-amber-900 dark:text-amber-200',
                        status === 'LEARNING' && 'bg-slate-100 text-slate-800 dark:bg-slate-700 dark:text-slate-300',
                        status === 'NOT_STARTED' && 'bg-slate-100 text-slate-600 dark:bg-slate-700 dark:text-slate-400',
                      )}>
                        {status}
                      </span>
                    </div>
                  ))
                )}
              </div>
            </div>
          )}
          
          {/* Data */}
          {activeSection === 'data' && (
            <div className="card p-6 space-y-6">
              <h2 className="text-lg font-semibold text-slate-900 dark:text-slate-100">Data Management</h2>
              
              <div className="space-y-4">
                <div className="p-4 border border-slate-200 dark:border-slate-700 rounded-lg">
                  <div className="flex items-center justify-between">
                    <div>
                      <h3 className="font-medium text-slate-900 dark:text-slate-100">Export Data</h3>
                      <p className="text-sm text-slate-500 dark:text-slate-400">Download a backup of all your settings and progress</p>
                    </div>
                    <button onClick={handleExport} className="btn-secondary flex items-center gap-2">
                      <Download className="w-4 h-4" /> Export
                    </button>
                  </div>
                </div>
                
                <div className="p-4 border border-slate-200 dark:border-slate-700 rounded-lg">
                  <div className="flex items-center justify-between">
                    <div>
                      <h3 className="font-medium text-slate-900 dark:text-slate-100">Import Data</h3>
                      <p className="text-sm text-slate-500 dark:text-slate-400">Restore settings and progress from a backup file</p>
                    </div>
                    <label className="btn-secondary flex items-center gap-2 cursor-pointer">
                      <Upload className="w-4 h-4" /> Import
                      <input type="file" accept=".json" className="sr-only" onChange={(e) => e.target.files?.[0] && handleImport(e.target.files[0])} />
                    </label>
                  </div>
                </div>
                
                {importMessage && (
                  <div className={clsx('p-3 rounded-lg text-sm flex items-center gap-2',
                    importMessage.type === 'success' ? 'bg-green-100 text-green-800 dark:bg-green-900 dark:text-green-200' : 'bg-red-100 text-red-800 dark:bg-red-900 dark:text-red-200'
                  )}>
                    {importMessage.type === 'success' ? <CheckCircle className="w-4 h-4" /> : <AlertCircle className="w-4 h-4" />}
                    {importMessage.text}
                  </div>
                )}
                
                <div className="p-4 border border-red-200 dark:border-red-800 rounded-lg bg-red-50 dark:bg-red-900/20">
                  <div className="flex items-center justify-between">
                    <div>
                      <h3 className="font-medium text-red-800 dark:text-red-200">Danger Zone</h3>
                      <p className="text-sm text-red-600 dark:text-red-400">Permanently delete all local data</p>
                    </div>
                    <button onClick={handleClearAllData} className="btn-danger flex items-center gap-2">
                      <Trash2 className="w-4 h-4" /> Clear All Data
                    </button>
                  </div>
                </div>
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}