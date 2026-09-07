import { ReactNode } from 'react';
import { Outlet, NavLink, useLocation } from 'react-router-dom';
import { Menu, X, ChevronRight, ChevronDown, BookOpen, Code, Zap, Brain, Settings, Sun, Moon, Monitor } from 'lucide-react';
import { useUIStore } from '@/store';
import clsx from 'clsx';

const navigation = [
  { name: 'Curriculum', href: '/curriculum', icon: BookOpen },
  { name: 'Patterns', href: '/curriculum?view=patterns', icon: Zap },
  { name: 'Algorithms', href: '/curriculum?view=algorithms', icon: Brain },
  { name: 'Data Structures', href: '/curriculum?view=data-structures', icon: Code },
];

export function Layout({ children }: { children?: ReactNode }) {
  const { sidebarOpen, toggleSidebar, theme, setTheme } = useUIStore();
  const location = useLocation();
  
  const toggleTheme = () => {
    const themes: ('light' | 'dark' | 'system')[] = ['light', 'dark', 'system'];
    const currentIndex = themes.indexOf(theme);
    setTheme(themes[(currentIndex + 1) % themes.length]);
  };
  
  const getThemeIcon = () => {
    if (theme === 'system') return <Monitor className="w-5 h-5" />;
    if (theme === 'dark') return <Moon className="w-5 h-5" />;
    return <Sun className="w-5 h-5" />;
  };
  
  return (
    <div className="min-h-screen bg-slate-50 dark:bg-slate-950 transition-colors duration-200">
      {/* Mobile sidebar overlay */}
      <div
        className={clsx(
          'fixed inset-0 z-40 bg-slate-900/50 transition-opacity lg:hidden',
          sidebarOpen ? 'opacity-100 pointer-events-auto' : 'opacity-0 pointer-events-none'
        )}
        onClick={toggleSidebar}
        aria-hidden="true"
      />
      
      {/* Sidebar */}
      <aside
        className={clsx(
          'fixed lg:static inset-y-0 left-0 z-50 w-64 bg-white dark:bg-slate-900 border-r border-slate-200 dark:border-slate-700 transform transition-transform duration-200 flex flex-col',
          sidebarOpen ? 'translate-x-0' : '-translate-x-full lg:translate-x-0'
        )}
        aria-label="Main navigation"
      >
        <div className="flex h-16 items-center justify-between px-4 border-b border-slate-200 dark:border-slate-700">
          <h1 className="text-xl font-bold text-slate-900 dark:text-slate-100">
            DSA Mastery
          </h1>
          <button
            className="lg:hidden p-2 rounded-lg hover:bg-slate-100 dark:hover:bg-slate-800"
            onClick={toggleSidebar}
            aria-label="Close sidebar"
          >
            <X className="w-5 h-5" />
          </button>
        </div>
        
        <nav className="flex-1 p-4 space-y-1 overflow-y-auto" aria-label="Main navigation">
          {navigation.map((item) => {
            const isActive = location.pathname.startsWith(item.href.split('?')[0]);
            return (
              <NavLink
                key={item.name}
                to={item.href}
                className={clsx(
                  'flex items-center gap-3 px-3 py-2 rounded-lg text-sm font-medium transition-colors',
                  isActive
                    ? 'bg-primary-50 text-primary-700 dark:bg-primary-900/30 dark:text-primary-300'
                    : 'text-slate-600 hover:bg-slate-100 dark:text-slate-400 dark:hover:bg-slate-800'
                )}
                aria-current={isActive ? 'page' : undefined}
              >
                <item.icon className="w-5 h-5 flex-shrink-0" aria-hidden="true" />
                {item.name}
              </NavLink>
            );
          })}
        </nav>
        
        <div className="p-4 border-t border-slate-200 dark:border-slate-700 space-y-2">
          <button
            onClick={toggleTheme}
            className="w-full flex items-center gap-3 px-3 py-2 rounded-lg text-sm font-medium text-slate-600 hover:bg-slate-100 dark:text-slate-400 dark:hover:bg-slate-800 transition-colors"
            aria-label={`Current theme: ${theme}. Click to change.`}
          >
            {getThemeIcon()}
            <span className="capitalize">{theme}</span>
          </button>
          
          <NavLink
            to="/settings"
            className="flex items-center gap-3 px-3 py-2 rounded-lg text-sm font-medium text-slate-600 hover:bg-slate-100 dark:text-slate-400 dark:hover:bg-slate-800 transition-colors"
          >
            <Settings className="w-5 h-5" aria-hidden="true" />
            Settings
          </NavLink>
        </div>
      </aside>
      
      {/* Main content */}
      <main
        className={clsx(
          'lg:pl-64 min-h-screen transition-all duration-200',
          sidebarOpen ? 'lg:pl-64' : 'lg:pl-64'
        )}
      >
        <header className="sticky top-0 z-30 h-16 bg-white/80 dark:bg-slate-950/80 backdrop-blur-sm border-b border-slate-200 dark:border-slate-700">
          <div className="flex h-full items-center justify-between px-4 lg:px-6">
            <button
              className="lg:hidden p-2 rounded-lg hover:bg-slate-100 dark:hover:bg-slate-800"
              onClick={toggleSidebar}
              aria-label="Open sidebar"
            >
              <Menu className="w-6 h-6" />
            </button>
            
            <div className="flex-1 lg:flex-none" />
            
            <div className="flex items-center gap-4">
              {/* User avatar placeholder */}
              <div className="w-8 h-8 rounded-full bg-primary-100 dark:bg-primary-900 flex items-center justify-center">
                <span className="text-sm font-medium text-primary-700 dark:text-primary-300">U</span>
              </div>
            </div>
          </div>
        </header>
        
        <div className="p-4 lg:p-6 max-w-7xl mx-auto">
          {children ?? <Outlet />}
        </div>
      </main>
    </div>
  );
}