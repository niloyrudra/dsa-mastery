import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { 
  problemsApi, 
  patternsApi, 
  algorithmsApi, 
  dataStructuresApi, 
  templatesApi, 
  curriculumApi, 
  executionApi, 
  masteryApi, 
  aiApi,
  searchApi 
} from '@/services/api';
import type { 
  ProblemSummaryDto, 
  ProblemChapterDto, 
  PatternDetailDto,
  AlgorithmDetailDto,
  DataStructureDetailDto,
  TemplateDetailDto,
  CurriculumNodeDto,
  LearningPathDto,
  ExecutionRequest,
  ExecutionJobResponse,
  ExecutionResultDto,
  MasteryRecordDto,
  MasteryStatsDto,
  AIConversationRequest,
  AIConversationResponse,
  AIMessageDto,
  SearchResponse 
} from '../types';

// Problems
export function useProblems(params?: Parameters<typeof problemsApi.list>[0]) {
  return useQuery({
    queryKey: ['problems', params],
    queryFn: () => problemsApi.list(params),
    select: (data) => data.data,
  });
}

export function useProblem(slug: string) {
  return useQuery({
    queryKey: ['problem', slug],
    queryFn: () => problemsApi.get(slug),
    enabled: !!slug,
    staleTime: 1000 * 60 * 10,
    select: (data) => data.data,
  });
}

export function useProblemVariations(slug: string) {
  return useQuery({
    queryKey: ['problem', slug, 'variations'],
    queryFn: () => problemsApi.getVariations(slug),
    enabled: !!slug,
    select: (data) => data.data,
  });
}

export function useProblemSolutions(slug: string) {
  return useQuery({
    queryKey: ['problem', slug, 'solutions'],
    queryFn: () => problemsApi.getSolutions(slug),
    enabled: !!slug,
    select: (data) => data.data,
  });
}

export function useProblemTestCases(slug: string) {
  return useQuery({
    queryKey: ['problem', slug, 'testCases'],
    queryFn: () => problemsApi.getTestCases(slug),
    enabled: !!slug,
    select: (data) => data.data,
  });
}

export function useProblemMastery(slug: string) {
  return useQuery({
    queryKey: ['problem', slug, 'mastery'],
    queryFn: () => problemsApi.getMastery(slug),
    enabled: !!slug,
    select: (data) => data.data,
  });
}

// Patterns
export function usePatterns() {
  return useQuery({
    queryKey: ['patterns'],
    queryFn: () => patternsApi.list(),
    select: (data) => data.data,
  });
}

export function usePattern(slug: string) {
  return useQuery({
    queryKey: ['pattern', slug],
    queryFn: () => patternsApi.get(slug),
    enabled: !!slug,
    staleTime: 1000 * 60 * 10,
    select: (data) => data.data,
  });
}

// Algorithms
export function useAlgorithms() {
  return useQuery({
    queryKey: ['algorithms'],
    queryFn: () => algorithmsApi.list(),
    select: (data) => data.data,
  });
}

export function useAlgorithm(slug: string) {
  return useQuery({
    queryKey: ['algorithm', slug],
    queryFn: () => algorithmsApi.get(slug),
    enabled: !!slug,
    select: (data) => data.data,
  });
}

// Data Structures
export function useDataStructures() {
  return useQuery({
    queryKey: ['dataStructures'],
    queryFn: () => dataStructuresApi.list(),
    select: (data) => data.data,
  });
}

export function useDataStructure(slug: string) {
  return useQuery({
    queryKey: ['dataStructure', slug],
    queryFn: () => dataStructuresApi.get(slug),
    enabled: !!slug,
    select: (data) => data.data,
  });
}

// Templates
export function useTemplates(params?: Parameters<typeof templatesApi.list>[0]) {
  return useQuery({
    queryKey: ['templates', params],
    queryFn: () => templatesApi.list(params),
    select: (data) => data.data,
  });
}

export function useTemplate(slug: string) {
  return useQuery({
    queryKey: ['template', slug],
    queryFn: () => templatesApi.get(slug),
    enabled: !!slug,
    select: (data) => data.data,
  });
}

// Curriculum
export function useCurriculum() {
  return useQuery({
    queryKey: ['curriculum'],
    queryFn: () => curriculumApi.getTree(),
    select: (data) => data.data,
  });
}

export function useLearningPath(problemSlug: string) {
  return useQuery({
    queryKey: ['learningPath', problemSlug],
    queryFn: () => curriculumApi.getLearningPath(problemSlug),
    enabled: !!problemSlug,
    select: (data) => data.data,
  });
}

// Execution
export function useExecutionSubmit() {
  const queryClient = useQueryClient();
  
  return useMutation({
    mutationFn: (request: ExecutionRequest) => executionApi.submit(request),
    onSuccess: (data) => {
      // Poll for result
      pollExecutionResult(data.data.jobId, queryClient);
    },
  });
}

export function useExecutionResult(jobId: string, enabled = true) {
  return useQuery({
    queryKey: ['execution', jobId],
    queryFn: () => executionApi.getJob(jobId),
    enabled: enabled && !!jobId,
    select: (data) => data.data,
    refetchInterval: (query) => {
      const status = query.state.data?.data?.status;
      return status === 'QUEUED' || status === 'COMPILING' || status === 'RUNNING' ? 1000 : false;
    },
  });
}

function pollExecutionResult(jobId: string, queryClient: ReturnType<typeof useQueryClient>) {
  const interval = setInterval(() => {
    queryClient.invalidateQueries({ queryKey: ['execution', jobId] });
  }, 1000);
  
  // Stop polling after 60 seconds or when completed
  const checkComplete = setInterval(async () => {
    try {
      const { data } = await executionApi.getJob(jobId);
      if (data.status !== 'QUEUED' && data.status !== 'COMPILING' && data.status !== 'RUNNING') {
        clearInterval(interval);
        clearInterval(checkComplete);
        queryClient.invalidateQueries({ queryKey: ['execution', jobId] });
      }
    } catch {
      clearInterval(interval);
      clearInterval(checkComplete);
    }
  }, 2000);
}

export function useExecutionHistory(problemId?: string) {
  return useQuery({
    queryKey: ['executionHistory', problemId],
    queryFn: () => executionApi.getHistory(problemId),
    select: (data) => data.data,
  });
}

// Mastery
export function useMasteryRecords(params?: Parameters<typeof masteryApi.getAll>[0]) {
  return useQuery({
    queryKey: ['mastery', params],
    queryFn: () => masteryApi.getAll(params),
    select: (data) => data.data,
  });
}

export function useMasteryStats() {
  return useQuery({
    queryKey: ['mastery', 'stats'],
    queryFn: () => masteryApi.getStats(),
    select: (data) => data.data,
  });
}

export function useUpdateMastery() {
  const queryClient = useQueryClient();
  
  return useMutation({
    mutationFn: ({ entityType, entityId, data }: { entityType: string; entityId: string; data: Partial<MasteryRecordDto> }) => 
      masteryApi.update(entityType, entityId, data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['mastery'] });
    },
  });
}

export function useReviewStats() {
  return useQuery({
    queryKey: ['mastery', 'review', 'stats'],
    queryFn: () => masteryApi.getReviewStats(),
    select: (data) => data.data,
  });
}

export function useRecommendedReviews(limit = 10) {
  return useQuery({
    queryKey: ['mastery', 'review', 'due', limit],
    queryFn: () => masteryApi.getDueReviews(limit),
    select: (data) => data.data,
  });
}

export function useAllReviews() {
  return useQuery({
    queryKey: ['mastery', 'review', 'all'],
    queryFn: () => masteryApi.getAllReviews(),
    select: (data) => data.data,
  });
}

export function useRecordReview() {
  const queryClient = useQueryClient();
  
  return useMutation({
    mutationFn: ({ entityType, entityId, quality }: { entityType: string; entityId: string; quality: 'AGAIN' | 'HARD' | 'GOOD' | 'EASY' }) => 
      masteryApi.recordReview(entityType, entityId, quality),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['mastery'] });
    },
  });
}

// AI
export function useStartAIConversation() {
  return useMutation({
    mutationFn: (request: AIConversationRequest) => aiApi.startConversation(request),
  });
}

export function useAISendMessage() {
  return useMutation({
    mutationFn: ({ conversationId, request }: { conversationId: string; request: Parameters<typeof aiApi.sendMessage>[1] }) => 
      aiApi.sendMessage(conversationId, request),
  });
}

export function useAIConversation(conversationId: string) {
  return useQuery({
    queryKey: ['aiConversation', conversationId],
    queryFn: () => aiApi.getConversation(conversationId),
    enabled: !!conversationId,
    select: (data) => data.data,
  });
}

export function useAIConversations(params?: Parameters<typeof aiApi.listConversations>[0]) {
  return useQuery({
    queryKey: ['aiConversations', params],
    queryFn: () => aiApi.listConversations(params),
    select: (data) => data.data,
  });
}

// Search
export function useSearch(query: string, type?: string, limit = 10) {
  return useQuery({
    queryKey: ['search', query, type, limit],
    queryFn: () => searchApi.search({ q: query, type, limit }),
    enabled: query.length >= 2,
    staleTime: 1000 * 60 * 5,
    select: (data) => data.data,
  });
}