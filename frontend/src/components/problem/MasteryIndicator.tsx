import { BookOpen, CheckCircle, Target, Award, RotateCcw, AlertCircle } from 'lucide-react';
import clsx from 'clsx';
import type { MasteryStatus } from '@/types';

interface MasteryIndicatorProps {
  status: MasteryStatus;
  score?: number;
  showLabel?: boolean;
  size?: 'sm' | 'md' | 'lg';
}

const statusConfig: Record<MasteryStatus, { label: string; icon: React.ReactNode; color: string; bgColor: string }> = {
  NOT_STARTED: { 
    label: 'Not Started', 
    icon: <BookOpen className="w-4 h-4" />, 
    color: 'text-slate-600 dark:text-slate-400', 
    bgColor: 'bg-slate-100 dark:bg-slate-800' 
  },
  LEARNING: { 
    label: 'Learning', 
    icon: <RotateCcw className="w-4 h-4 animate-spin" />, 
    color: 'text-slate-700 dark:text-slate-300', 
    bgColor: 'bg-slate-100 dark:bg-slate-800' 
  },
  PRACTICING: { 
    label: 'Practicing', 
    icon: <Target className="w-4 h-4" />, 
    color: 'text-amber-700 dark:text-amber-300', 
    bgColor: 'bg-amber-100 dark:bg-amber-900/30' 
  },
  SOLVED: { 
    label: 'Solved', 
    icon: <CheckCircle className="w-4 h-4" />, 
    color: 'text-blue-700 dark:text-blue-300', 
    bgColor: 'bg-blue-100 dark:bg-blue-900/30' 
  },
  MASTERED: { 
    label: 'Mastered', 
    icon: <Award className="w-4 h-4" />, 
    color: 'text-emerald-700 dark:text-emerald-300', 
    bgColor: 'bg-emerald-100 dark:bg-emerald-900/30' 
  },
  NEEDS_REVIEW: { 
    label: 'Needs Review', 
    icon: <AlertCircle className="w-4 h-4" />, 
    color: 'text-red-700 dark:text-red-300', 
    bgColor: 'bg-red-100 dark:bg-red-900/30' 
  },
};

const sizeClasses = {
  sm: 'px-2 py-1 text-xs gap-1',
  md: 'px-3 py-1.5 text-sm gap-2',
  lg: 'px-4 py-2 text-base gap-3',
};

const iconSizes = {
  sm: 'w-3 h-3',
  md: 'w-4 h-4',
  lg: 'w-5 h-5',
};

export function MasteryIndicator({ status, score, showLabel = true, size = 'md' }: MasteryIndicatorProps) {
  const config = statusConfig[status];
  const Icon = config.icon;
  
  return (
    <div className={clsx(
      'inline-flex items-center rounded-full border',
      config.color,
      config.bgColor.replace('bg-', 'border-').replace('dark:bg-', 'dark:border-'),
      sizeClasses[size]
    )}>
      <span className={clsx(config.color, iconSizes[size])}>
        {Icon}
      </span>
      {showLabel && (
        <span className={clsx('font-medium', config.color)}>
          {config.label}
        </span>
      )}
      {score !== undefined && showLabel && (
        <span className={clsx('ml-2 px-1.5 py-0.5 rounded text-xs font-mono', config.bgColor, config.color)}>
          {score}%
        </span>
      )}
    </div>
  );
}

export function MasteryProgressRing({ 
  status, 
  score = 0, 
  size = 60, 
  strokeWidth = 6 
}: { 
  status: MasteryStatus; 
  score?: number; 
  size?: number; 
  strokeWidth?: number;
}) {
  const config = statusConfig[status];
  const circumference = 2 * Math.PI * (size / 2 - strokeWidth);
  const offset = circumference - (score / 100) * circumference;
  
  return (
    <div className="relative inline-flex items-center justify-center" style={{ width: size, height: size }}>
      <svg style={{ width: size, height: size, transform: 'rotate(-90deg)' }}>
        <circle
          cx={size / 2}
          cy={size / 2}
          r={size / 2 - strokeWidth}
          fill="none"
          stroke={config.bgColor.replace('bg-', '').replace('dark:bg-', '').replace('/30', '')}
          strokeWidth={strokeWidth}
          className="text-slate-200 dark:text-slate-700"
        />
        <circle
          cx={size / 2}
          cy={size / 2}
          r={size / 2 - strokeWidth}
          fill="none"
          stroke={config.color.replace('text-', '')}
          strokeWidth={strokeWidth}
          strokeDasharray={circumference}
          strokeDashoffset={offset}
          strokeLinecap="round"
          className="transition-all duration-500"
        />
      </svg>
      <div className="absolute inset-0 flex items-center justify-center">
        <span className={clsx('font-bold', config.color, size < 80 ? 'text-sm' : 'text-lg')}>
          {score}%
        </span>
      </div>
    </div>
  );
}