import { useState } from 'react';
import { Link } from 'react-router-dom';
import { ChevronRight, ChevronDown, FolderOpen, Folder, FileCode, Zap, Brain, Database, Search, Filter } from 'lucide-react';
import { useCurriculum } from '@/hooks/useQueries';
import { useUIStore } from '@/store';
import clsx from 'clsx';
import type { CurriculumNodeDto } from '@/types';

interface CurriculumTreeProps {
  node: CurriculumNodeDto;
  depth?: number;
  expandedPaths?: Set<string>;
  onToggle: (path: string) => void;
}

function CurriculumTree({ node, depth = 0, expandedPaths, onToggle }: CurriculumTreeProps) {
  const path = `${node.type.toLowerCase()}-${node.id}`;
  const isExpanded = expandedPaths?.has(path);
  const hasChildren = node.children && node.children.length > 0;
  
  const getIcon = () => {
    switch (node.type) {
      case 'CATEGORY':
        return isExpanded ? <FolderOpen className="w-5 h-5 text-amber-500" /> : <Folder className="w-5 h-5 text-amber-500" />;
      case 'PATTERN':
        return <Zap className="w-5 h-5 text-purple-500" />;
      case 'ALGORITHM':
        return <Brain className="w-5 h-5 text-blue-500" />;
      case 'PROBLEM':
        return <FileCode className="w-5 h-5 text-green-500" />;
      default:
        return <FileCode className="w-5 h-5" />;
    }
  };
  
  const getBadge = () => {
    if (node.type === 'PROBLEM' && node.problemId) {
      return (
        <span className="ml-auto text-xs px-2 py-0.5 rounded bg-slate-100 dark:bg-slate-800 text-slate-600 dark:text-slate-400">
          Problem
        </span>
      );
    }
    if (hasChildren) {
      return (
        <span className="ml-auto text-xs text-slate-400">
          {node.children?.length} items
        </span>
      );
    }
    return null;
  };
  
  return (
    <div className="select-none">
      <div
        className={clsx(
          'flex items-center gap-2 py-1.5 px-2 rounded-lg transition-colors cursor-pointer',
          'hover:bg-slate-100 dark:hover:bg-slate-800',
          depth > 0 && 'pl-6',
          depth > 1 && 'pl-4',
        )}
        style={{ paddingLeft: `${depth * 1.5 + 0.5}rem` }}
        onClick={() => hasChildren && onToggle(path)}
      >
        {hasChildren && (
          <button
            className="p-1 rounded hover:bg-slate-200 dark:hover:bg-slate-700 flex-shrink-0"
            onClick={(e) => { e.stopPropagation(); onToggle(path); }}
            aria-label={isExpanded ? `Collapse ${node.name}` : `Expand ${node.name}`}
            aria-expanded={isExpanded}
          >
            {isExpanded ? <ChevronDown className="w-4 h-4" /> : <ChevronRight className="w-4 h-4" />}
          </button>
        )}
        {!hasChildren && <span className="w-6 flex-shrink-0" />}
        
        {getIcon()}
        
        {node.type === 'PROBLEM' && node.problemId ? (
          <Link
            to={`/problem/${node.problemId}`}
            className="flex-1 text-left text-sm font-medium text-slate-700 dark:text-slate-300 hover:text-primary-600 dark:hover:text-primary-400 truncate"
          >
            {node.name}
          </Link>
        ) : node.type === 'PATTERN' && node.patternId ? (
          <Link
            to={`/pattern/${node.patternId}`}
            className="flex-1 text-left text-sm font-medium text-slate-700 dark:text-slate-300 hover:text-primary-600 dark:hover:text-primary-400 truncate"
          >
            {node.name}
          </Link>
        ) : node.type === 'ALGORITHM' && node.algorithmId ? (
          <Link
            to={`/algorithm/${node.algorithmId}`}
            className="flex-1 text-left text-sm font-medium text-slate-700 dark:text-slate-300 hover:text-primary-600 dark:hover:text-primary-400 truncate"
          >
            {node.name}
          </Link>
        ) : (
          <span className="flex-1 text-left text-sm font-medium text-slate-700 dark:text-slate-300 truncate">
            {node.name}
          </span>
        )}
        
        {getBadge()}
      </div>
      
      {hasChildren && isExpanded && (
        <div className="mt-1 space-y-1 border-l border-slate-200 dark:border-slate-700 pl-2">
          {node.children?.map((child) => (
            <CurriculumTree
              key={child.id}
              node={child}
              depth={depth + 1}
              expandedPaths={expandedPaths}
              onToggle={onToggle}
            />
          ))}
        </div>
      )}
    </div>
  );
}

export function CurriculumPage() {
  const { data: curriculum, isLoading, error } = useCurriculum();
  const [expandedPaths, setExpandedPaths] = useState<Set<string>>(new Set());
  const [searchQuery, setSearchQuery] = useState('');
  const [filterType, setFilterType] = useState<'all' | 'patterns' | 'algorithms' | 'problems'>('all');
  const { sidebarOpen, toggleSidebar } = useUIStore();
  
  // Auto-expand first level categories
  if (expandedPaths.size === 0 && curriculum) {
    curriculum.children?.forEach((cat) => {
      setExpandedPaths((prev) => new Set(prev).add(`category-${cat.id}`));
    });
  }
  
  const toggleNode = (path: string) => {
    setExpandedPaths((prev) => {
      const next = new Set(prev);
      if (next.has(path)) next.delete(path);
      else next.add(path);
      return next;
    });
  };
  
  const filteredNodes = curriculum?.children?.filter((node) => {
    if (filterType !== 'all') {
      const typeMap = {
        patterns: 'PATTERN',
        algorithms: 'ALGORITHM',
        problems: 'PROBLEM',
      };
      if (node.type !== typeMap[filterType]) {
        // Check children
        return node.children?.some((c) => c.type === typeMap[filterType]) ?? false;
      }
    }
    if (searchQuery) {
      const matches = node.name.toLowerCase().includes(searchQuery.toLowerCase());
      const childMatches = node.children?.some((c) => 
        c.name.toLowerCase().includes(searchQuery.toLowerCase())
      ) ?? false;
      return matches || childMatches;
    }
    return true;
  });
  
  if (isLoading) {
    return (
      <div className="flex items-center justify-center h-64">
        <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-primary-600" />
      </div>
    );
  }
  
  if (error) {
    return (
      <div className="text-center py-12">
        <p className="text-red-600 dark:text-red-400">Failed to load curriculum</p>
      </div>
    );
  }
  
  return (
    <div className="space-y-6">
      <div className="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-4">
        <div>
          <h1 className="text-2xl font-bold text-slate-900 dark:text-slate-100">Curriculum</h1>
          <p className="text-slate-500 dark:text-slate-400 mt-1">
            Structured learning path for DSA mastery
          </p>
        </div>
        
        <div className="flex flex-col sm:flex-row gap-3">
          <div className="relative flex-1 max-w-md">
            <Search className="absolute left-3 top-1/2 -translate-y-1/2 w-5 h-5 text-slate-400" />
            <input
              type="search"
              placeholder="Search problems, patterns, algorithms..."
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
              className="w-full pl-10 pr-4 py-2 bg-white dark:bg-slate-800 border border-slate-300 dark:border-slate-600 rounded-lg text-sm focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-primary-500"
            />
          </div>
          
          <select
            value={filterType}
            onChange={(e) => setFilterType(e.target.value as typeof filterType)}
            className="px-4 py-2 bg-white dark:bg-slate-800 border border-slate-300 dark:border-slate-600 rounded-lg text-sm focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-primary-500"
          >
            <option value="all">All</option>
            <option value="patterns">Patterns</option>
            <option value="algorithms">Algorithms</option>
            <option value="problems">Problems</option>
          </select>
        </div>
      </div>
      
      <div className="card overflow-hidden">
        <div className="p-4 border-b border-slate-200 dark:border-slate-700">
          <div className="flex flex-wrap gap-2">
            <span className="px-3 py-1 text-xs font-medium bg-primary-100 dark:bg-primary-900 text-primary-700 dark:text-primary-300 rounded-full">
              {curriculum?.children?.length ?? 0} Categories
            </span>
            <span className="px-3 py-1 text-xs font-medium bg-purple-100 dark:bg-purple-900 text-purple-700 dark:text-purple-300 rounded-full">
              Patterns
            </span>
            <span className="px-3 py-1 text-xs font-medium bg-blue-100 dark:bg-blue-900 text-blue-700 dark:text-blue-300 rounded-full">
              Algorithms
            </span>
            <span className="px-3 py-1 text-xs font-medium bg-green-100 dark:bg-green-900 text-green-700 dark:text-green-300 rounded-full">
              Problems
            </span>
          </div>
        </div>
        
        <div className="p-4 max-h-[calc(100vh-280px)] overflow-y-auto scrollbar-thin">
          {filteredNodes?.map((node) => (
            <CurriculumTree
              key={node.id}
              node={node}
              expandedPaths={expandedPaths}
              onToggle={toggleNode}
            />
          ))}
          
          {filteredNodes?.length === 0 && (
            <div className="text-center py-12 text-slate-500 dark:text-slate-400">
              No items match your search/filter
            </div>
          )}
        </div>
      </div>
    </div>
  );
}