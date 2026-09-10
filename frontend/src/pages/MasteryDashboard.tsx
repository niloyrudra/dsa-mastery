import { useState, useMemo } from 'react';
import { 
  BookOpen, 
  Zap, 
  Code2, 
  Database, 
  Brain, 
  RotateCcw, 
  Clock, 
  TrendingUp,
  Filter,
  ChevronDown,
  ChevronRight,
  CheckCircle,
  AlertCircle,
  XCircle,
  Loader2,
  Star,
  Award,
  Target,
  BarChart2
} from 'lucide-react';
import { 
  useMasteryRecords, 
  useMasteryStats, 
  useReviewStats, 
  useRecommendedReviews 
} from '@/hooks/useQueries';
import { useUpdateMastery } from '@/hooks/useQueries';
import { masteryApi } from '@/services/api';
import clsx from 'clsx';
import type { MasteryRecordDto, ReviewDto } from '@/types';

const STATUS_CONFIG = {
  MASTERED: { icon: CheckCircle, color: 'text-green-500', bg: 'bg-green-100 dark:bg-green-900/30', label: 'Mastered' },
  SOLVED: { icon: CheckCircle, color: 'text-blue-500', bg: 'bg-blue-100 dark:bg-blue-900/30', label: 'Solved' },
  PRACTICING: { icon: Target, color: 'text-yellow-500', bg: 'bg-yellow-100 dark:bg-yellow-900/30', label: 'Practicing' },
  LEARNING: { icon: BookOpen, color: 'text-purple-500', bg: 'bg-purple-100 dark:bg-purple-900/30', label: 'Learning' },
  NOT_STARTED: { icon: XCircle, color: 'text-slate-400', bg: 'bg-slate-100 dark:bg-slate-800/30', label: 'Not Started' },
} as const;

const ENTITY_TYPE_CONFIG = {
  PROBLEM: { icon: Code2, color: 'text-green-500', label: 'Problem' },
  PATTERN: { icon: Zap, color: 'text-purple-500', label: 'Pattern' },
  ALGORITHM: { icon: Brain, color: 'text-blue-500', label: 'Algorithm' },
  DATA_STRUCTURE: { icon: Database, color: 'text-orange-500', label: 'Data Structure' },
  TEMPLATE: { icon: Database, color: 'text-cyan-500', label: 'Template' },
  CONCEPT: { icon: BookOpen, color: 'text-pink-500', label: 'Concept' },
} as const;

const REVIEW_QUALITY_LABELS = {
  AGAIN: { label: 'Again', desc: 'Forgot completely', color: 'bg-red-100 text-red-700 dark:bg-red-900 dark:text-red-300' },
  HARD: { label: 'Hard', desc: 'Recalled with difficulty', color: 'bg-orange-100 text-orange-700 dark:bg-orange-900 dark:text-orange-300' },
  GOOD: { label: 'Good', desc: 'Recalled correctly', color: 'bg-blue-100 text-blue-700 dark:bg-blue-900 dark:text-blue-300' },
  EASY: { label: 'Easy', desc: 'Recalled effortlessly', color: 'bg-green-100 text-green-700 dark:bg-green-900 dark:text-green-300' },
} as const;

function StatCard({ title, value, change, icon: Icon, color }: { 
  title: string; 
  value: string | number; 
  change?: string;
  icon: React.ComponentType<{ className?: string }>;
  color: string;
}) {
  return (
    <div className="p-5 bg-white dark:bg-slate-800 rounded-xl border border-slate-200 dark:border-slate-700">
      <div className="flex items-center justify-between">
        <div>
          <p className="text-sm text-slate-500 dark:text-slate-400">{title}</p>
          <p className="text-3xl font-bold text-slate-900 dark:text-slate-100 mt-1">{value}</p>
          {change && <p className="text-xs text-green-600 dark:text-green-400 mt-1">{change}</p>}
        </div>
        <div className={clsx('p-3 rounded-xl', color)}>
          <Icon className="w-7 h-7" />
        </div>
      </div>
    </div>
  );
}

function MasteryCard({ record, onReview }: { 
  record: MasteryRecordDto; 
  onReview: (entityId: string, entityType: string) => void;
}) {
  const statusConfig = STATUS_CONFIG[record.status as keyof typeof STATUS_CONFIG] || STATUS_CONFIG.NOT_STARTED;
  const typeConfig = ENTITY_TYPE_CONFIG[record.entityType as keyof typeof ENTITY_TYPE_CONFIG] || { 
    icon: Code2, color: 'text-slate-500', label: record.entityType 
  };
  const StatusIcon = statusConfig.icon;
  const TypeIcon = typeConfig.icon;

  return (
    <div className="group p-4 bg-white dark:bg-slate-800 rounded-xl border border-slate-200 dark:border-slate-700 hover:border-primary-300 dark:hover:border-primary-700 transition-colors">
      <div className="flex items-start justify-between gap-4">
        <div className="flex items-center gap-3">
          <div className={clsx('p-2 rounded-lg', statusConfig.bg)}>
            <StatusIcon className={clsx('w-5 h-5', statusConfig.color)} />
          </div>
          <div>
            <div className="flex items-center gap-2">
              <span className="font-semibold text-slate-900 dark:text-slate-100 truncate max-w-[200px]">{record.name || record.slug}</span>
              <TypeIcon className={clsx('w-4 h-4', typeConfig.color)} title={typeConfig.label} />
            </div>
            <div className="flex items-center gap-2 text-sm text-slate-500 dark:text-slate-400 mt-1">
              <span className={clsx('px-2 py-0.5 rounded-full text-xs font-medium', statusConfig.bg)}>
                {statusConfig.label}
              </span>
              <span className="text-slate-400">•</span>
              <span>Score: {record.score}%</span>
              {record.attempts > 0 && (
                <>
                  <span className="text-slate-400">•</span>
                  <span>{record.attempts} attempts</span>
                </>
              )}
            </div>
          </div>
        </div>
        
        <div className="flex items-center gap-2 flex-shrink-0">
          <div className="w-16 h-16 rounded-full bg-slate-100 dark:bg-slate-800 flex items-center justify-center relative">
            <svg className="w-16 h-16 transform -rotate-90" viewBox="0 0 100 100">
              <circle 
                cx="50" cy="50" r="45" 
                fill="none" stroke="#e2e8f0" strokeWidth="8"
              />
              <circle 
                cx="50" cy="50" r="45" 
                fill="none" stroke="url(#progress-gradient)" strokeWidth="8" 
                strokeDasharray={`${record.score * 2.83} 283`}
                strokeDashoffset="283"
                strokeLinecap="round"
                className="transition-all duration-500"
              />
              <defs>
                <linearGradient id="progress-gradient" x1="0" y1="0" x2="100" y2="0">
                  <stop offset="0%" stopColor="#22c55e" />
                  <stop offset="100%" stopColor="#3b82f6" />
                </linearGradient>
              </defs>
            </svg>
            <span className="absolute inset-0 flex items-center justify-center text-2xl font-bold text-slate-900 dark:text-slate-100">
              {record.score}%
            </span>
          </div>
        </div>
      </div>
      
      <div className="mt-4 pt-4 border-t border-slate-100 dark:border-slate-700">
        <div className="flex items-center justify-between">
          <div className="flex items-center gap-4 text-sm text-slate-500 dark:text-slate-400">
            {record.lastPracticed && (
              <span className="flex items-center gap-1">
                <Clock className="w-4 h-4" />
                Last: {new Date(record.lastPracticed).toLocaleDateString()}
              </span>
            )}
            {record.masteredAt && (
              <span className="flex items-center gap-1 text-green-600 dark:text-green-400">
                <Award className="w-4 h-4" />
                Mastered: {new Date(record.masteredAt).toLocaleDateString()}
              </span>
            )}
          </div>
          
          <button
            onClick={() => onReview(record.entityId, record.entityType)}
            className="btn-primary text-sm px-4 py-1.5 flex items-center gap-2"
          >
            <RotateCcw className="w-4 h-4" />
            Review
          </button>
        </div>
      </div>
    </div>
  );
}

function ReviewCard({ review, onReview }: { 
  review: ReviewDto; 
  onReview: (entityId: string, entityType: string, quality: 'AGAIN' | 'HARD' | 'GOOD' | 'EASY') => void;
}) {
  const statusConfig = review.overdue 
    ? { color: 'text-red-500', bg: 'bg-red-100 dark:bg-red-900/30', label: 'Overdue' }
    : review.due
      ? { color: 'text-yellow-500', bg: 'bg-yellow-100 dark:bg-yellow-900/30', label: 'Due Now' }
      : { color: 'text-green-500', bg: 'bg-green-100 dark:bg-green-900/30', label: 'Upcoming' };

  const typeConfig = ENTITY_TYPE_CONFIG[review.entityType as keyof typeof ENTITY_TYPE_CONFIG] || { 
    icon: Code2, color: 'text-slate-500', label: review.entityType 
  };
  const TypeIcon = typeConfig.icon;

  const [showQuality, setShowQuality] = useState(false);

  return (
    <div className="p-4 bg-white dark:bg-slate-800 rounded-xl border border-slate-200 dark:border-slate-700">
      <div className="flex items-start justify-between gap-4 mb-3">
        <div className="flex items-center gap-3">
          <div className={clsx('p-2 rounded-lg', statusConfig.bg)}>
            <TypeIcon className={clsx('w-5 h-5', typeConfig.color)} />
          </div>
          <div>
            <h4 className="font-semibold text-slate-900 dark:text-slate-100">{review.entityId.slice(0, 8)}...</h4>
            <div className="flex items-center gap-2 text-sm text-slate-500 dark:text-slate-400 mt-1">
              <span className={clsx('px-2 py-0.5 rounded-full text-xs font-medium', statusConfig.bg)}>
                {statusConfig.label}
              </span>
              <span className="px-2 py-0.5 rounded-full text-xs font-medium bg-slate-100 dark:bg-slate-800">
                {typeConfig.label}
              </span>
            </div>
          </div>
        </div>
        
        <div className="text-right">
          <p className="text-xs text-slate-500 dark:text-slate-400">
            Next: {review.nextReviewDate ? new Date(review.nextReviewDate).toLocaleDateString() : 'N/A'}
          </p>
          <p className="text-xs text-slate-400">
            Score: {review.masteryScore}%
          </p>
        </div>
      </div>

      {!showQuality ? (
        <button
          onClick={() => setShowQuality(true)}
          className="w-full btn-secondary text-sm py-2 flex items-center justify-center gap-2"
        >
          <RotateCcw className="w-4 h-4" />
          Start Review
        </button>
      ) : (
        <div className="space-y-2" role="group" aria-label="Review quality">
          {(Object.keys(REVIEW_QUALITY_LABELS) as Array<keyof typeof REVIEW_QUALITY_LABELS>).map((quality) => {
            const config = REVIEW_QUALITY_LABELS[quality];
            return (
              <button
                key={quality}
                onClick={() => {
                  onReview(review.entityId, review.entityType, quality);
                  setShowQuality(false);
                }}
                className="w-full btn-secondary text-sm py-2 flex items-center justify-start gap-3 text-left"
              >
                <span className={clsx('px-2 py-0.5 rounded text-xs font-medium', config.color)}>
                  {config.label}
                </span>
                <span className="text-slate-600 dark:text-slate-400">{config.desc}</span>
              </button>
            );
          })}
        </div>
      )}
    </div>
  );
}

import { useState } from 'react';

export function MasteryDashboard() {
  const { data: masteryRecords, isLoading: masteryLoading } = useMasteryRecords();
  const { data: masteryStats, isLoading: statsLoading } = useMasteryStats();
  const { data: reviewStats } = useReviewStats();
  const { data: recommendedReviews } = useRecommendedReviews(10);
  const updateMastery = useUpdateMastery();
  const [activeTab, setActiveTab] = useState<'overview' | 'all' | 'reviews'>('overview');
  const [filterStatus, setFilterStatus] = useState<string>('all');
  const [filterType, setFilterType] = useState<string>('all');
  const [searchQuery, setSearchQuery] = useState('');

  const filteredRecords = useMemo(() => {
    if (!masteryRecords) return [];
    
    return masteryRecords.filter((record) => {
      if (filterStatus !== 'all' && record.status !== filterStatus) return false;
      if (filterType !== 'all' && record.entityType !== filterType) return false;
      if (searchQuery && !record.name?.toLowerCase().includes(searchQuery.toLowerCase())) return false;
      return true;
    });
  }, [masteryRecords, filterStatus, filterType, searchQuery]);

  if (masteryLoading || statsLoading) {
    return (
      <div className="flex items-center justify-center h-64">
        <Loader2 className="w-8 h-8 animate-spin text-primary-600" />
      </div>
    );
  }

  return (
    <div className="space-y-6">
      {/* Header */}
      <div className="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-4">
        <div>
          <h1 className="text-2xl font-bold text-slate-900 dark:text-slate-100">Mastery Dashboard</h1>
          <p className="text-slate-500 dark:text-slate-400 mt-1">
            Track your progress and review schedule
          </p>
        </div>
        
        <div className="flex items-center gap-2">
          <select
            value={filterType}
            onChange={(e) => setFilterType(e.target.value)}
            className="px-3 py-2 bg-white dark:bg-slate-800 border border-slate-300 dark:border-slate-600 rounded-lg text-sm"
          >
            <option value="all">All Types</option>
            <option value="PROBLEM">Problems</option>
            <option value="PATTERN">Patterns</option>
            <option value="ALGORITHM">Algorithms</option>
            <option value="DATA_STRUCTURE">Data Structures</option>
            <option value="TEMPLATE">Templates</option>
            <option value="CONCEPT">Concepts</option>
          </select>
          
          <select
            value={filterStatus}
            onChange={(e) => setFilterStatus(e.target.value)}
            className="px-3 py-2 bg-white dark:bg-slate-800 border border-slate-300 dark:border-slate-600 rounded-lg text-sm"
          >
            <option value="all">All Status</option>
            <option value="MASTERED">Mastered</option>
            <option value="SOLVED">Solved</option>
            <option value="PRACTICING">Practicing</option>
            <option value="LEARNING">Learning</option>
            <option value="NOT_STARTED">Not Started</option>
          </select>
        </div>
      </div>

      {/* Stats Grid */}
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
        <StatCard
          title="Total Items"
          value={masteryStats?.problems.total || 0}
          icon={Database}
          color="bg-blue-100 text-blue-600 dark:bg-blue-900/30 dark:text-blue-400"
        />
        <StatCard
          title="Mastered"
          value={masteryStats?.problems.mastered || 0}
          change={`${masteryStats?.overallProgress || 0}% overall`}
          icon={Award}
          color="bg-green-100 text-green-600 dark:bg-green-900/30 dark:text-green-400"
        />
        <StatCard
          title="Learning"
          value={masteryStats?.problems.learning || 0}
          icon={BookOpen}
          color="bg-purple-100 text-purple-600 dark:bg-purple-900/30 dark:text-purple-400"
        />
        <StatCard
          title="Due for Review"
          value={reviewStats?.dueCount || 0}
          change={reviewStats?.overdueCount ? `${reviewStats.overdueCount} overdue` : undefined}
          icon={RotateCcw}
          color="bg-orange-100 text-orange-600 dark:bg-orange-900/30 dark:text-orange-400"
        />
      </div>

      {/* Tabs */}
      <div className="border-b border-slate-200 dark:border-slate-700">
        <nav className="flex gap-1" aria-label="Dashboard sections">
          {[
            { id: 'overview', label: 'Overview', icon: BarChart2 },
            { id: 'all', label: 'All Items', icon: ListTree },
            { id: 'reviews', label: 'Reviews', icon: RotateCcw },
          ].map((tab) => (
            <button
              key={tab.id}
              onClick={() => setActiveTab(tab.id as any)}
              className={clsx(
                'tab flex items-center gap-2',
                activeTab === tab.id && 'tab-active'
              )}
            >
              <tab.icon className="w-4 h-4" />
              {tab.label}
            </button>
          ))}
        </nav>
      </div>

      {/* Tab Content */}
      {activeTab === 'overview' && (
        <div className="space-y-6">
          {/* Recommended Reviews */}
          {(recommendedReviews?.length ?? 0) > 0 && (
            <div>
              <h2 className="text-lg font-semibold text-slate-900 dark:text-slate-100 mb-4 flex items-center gap-2">
                <Target className="w-5 h-5 text-orange-500" />
                Recommended Reviews
              </h2>
              <div className="grid gap-3 sm:grid-cols-2 lg:grid-cols-3">
                {recommendedReviews?.slice(0, 6).map((review) => (
                  <ReviewCard
                    key={review.entityId}
                    review={review}
                    onReview={(entityId, entityType, quality) => {
                      masteryApi.startConversation({ problemId: entityId as any, modelRole: 'CODING' });
                    }}
                  />
                ))}
              </div>
            </div>
          )}

          {/* Recent Activity */}
          <div>
            <h2 className="text-lg font-semibold text-slate-900 dark:text-slate-100 mb-4 flex items-center gap-2">
              <TrendingUp className="w-5 h-5 text-blue-500" />
              Recently Practiced
            </h2>
            <div className="space-y-2 max-h-96 overflow-y-auto">
              {(() => {
                const recentRecords = masteryRecords
                  ?.filter(r => r.lastPracticed)
                  .sort((a, b) => new Date(b.lastPracticed!).getTime() - new Date(a.lastPracticed!).getTime())
                  .slice(0, 10) || [];
                
                if (recentRecords.length === 0) {
                  return (
                    <div className="text-center py-8 text-slate-500 dark:text-slate-400">
                      No recent activity
                    </div>
                  );
                }
                
                return recentRecords.map((record) => {
                  const StatusIcon = STATUS_CONFIG[record.status as keyof typeof STATUS_CONFIG]?.icon || XCircle;
                  const statusBg = STATUS_CONFIG[record.status as keyof typeof STATUS_CONFIG]?.bg || 'bg-slate-100';
                  
                  return (
                    <div key={record.entityId} className="p-3 bg-white dark:bg-slate-800 rounded-lg border border-slate-200 dark:border-slate-700 flex items-center justify-between gap-4">
                      <div className="flex items-center gap-3">
                        <div className={clsx('p-2 rounded-lg', statusBg)}>
                          <StatusIcon className="w-5 h-5 text-slate-500" />
                        </div>
                        <div>
                          <p className="font-medium text-slate-900 dark:text-slate-100 truncate max-w-[200px]">{record.name || record.slug}</p>
                          <p className="text-sm text-slate-500 dark:text-slate-400">
                            {record.entityType} • Score: {record.score}%
                          </p>
                        </div>
                      </div>
                      <span className="text-xs text-slate-400">
                        {record.lastPracticed ? new Date(record.lastPracticed).toLocaleString() : 'Never'}
                      </span>
                    </div>
                  );
                });
              })()}
            </div>
          </div>
        </div>
      )}

      {activeTab === 'all' && (
        <div className="space-y-4">
          <div className="flex items-center gap-3 mb-4">
            <div className="relative flex-1 max-w-md">
              <Search className="absolute left-3 top-1/2 -translate-y-1/2 w-5 h-5 text-slate-400" />
              <input
                type="search"
                placeholder="Search by name..."
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
                className="w-full pl-10 pr-4 py-2 bg-white dark:bg-slate-800 border border-slate-300 dark:border-slate-600 rounded-lg text-sm"
              />
            </div>
          </div>
          
          <div className="grid gap-3 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4">
            {filteredRecords.map((record) => (
              <MasteryCard
                key={`${record.entityType}-${record.entityId}`}
                record={record}
                onReview={(entityId, entityType) => {
                  // Navigate to review page or open modal
                }}
              />
            ))}
            
            {filteredRecords.length === 0 && (
              <div className="col-span-full text-center py-12 text-slate-500 dark:text-slate-400">
                No items match your filters
              </div>
            )}
          </div>
        </div>
      )}

      {activeTab === 'reviews' && (
        <div className="space-y-6">
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-3">
            {(recommendedReviews || []).map((review) => (
              <ReviewCard
                key={review.entityId}
                review={review}
                onReview={async (entityId, entityType, quality) => {
                  await masteryApi.startConversation({ problemId: entityId as any, modelRole: 'CODING' });
                }}
              />
            ))}
            
            {(recommendedReviews?.length || 0) === 0 && (
              <div className="col-span-full text-center py-12">
                <CheckCircle className="w-12 h-12 text-green-500 mx-auto mb-4" />
                <h3 className="text-lg font-semibold text-slate-900 dark:text-slate-100">All caught up!</h3>
                <p className="text-slate-500 dark:text-slate-400">No reviews due at the moment.</p>
              </div>
            )}
          </div>
          
          {reviewStats && (
            <div className="p-4 bg-slate-50 dark:bg-slate-800/50 rounded-xl border border-slate-200 dark:border-slate-700">
              <h3 className="font-semibold text-slate-900 dark:text-slate-100 mb-3">Review Schedule (Next 7 Days)</h3>
              <div className="flex items-end gap-2 h-32">
                {reviewStats.dailyDueNext7Days.map((count, index) => (
                  <div key={index} className="flex-1 flex flex-col items-center gap-1">
                    <div 
                      className="w-full bg-primary-500 rounded-t transition-all hover:bg-primary-600"
                      style={{ height: `${Math.max(4, (count / Math.max(1, ...reviewStats.dailyDueNext7Days)) * 100)}%` }}
                    />
                    <span className="text-xs text-slate-500 dark:text-slate-400">
                      {['Sun','Mon','Tue','Wed','Thu','Fri','Sat'][index]}
                    </span>
                    <span className="text-xs font-medium text-slate-900 dark:text-slate-100">{count}</span>
                  </div>
                ))}
              </div>
            </div>
          )}
        </div>
      )}
    </div>
  );
}

import { Search, ListTree } from 'lucide-react';