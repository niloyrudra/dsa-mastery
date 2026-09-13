import axios from 'axios';
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
  MasteryRecordDto as MasteryUpdateRequest,
  AIConversationRequest,
  AIConversationResponse,
  AIMessageRequest,
  AIMessageDto,
  SearchResponse,
  PaginatedResponse,
} from '@/types';

const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL || '/api/v1',
  headers: {
    'Content-Type': 'application/json',
  },
});

api.interceptors.response.use(
  (response) => response,
  (error) => {
    console.error('API Error:', error.response?.data || error.message);
    return Promise.reject(error);
  }
);

export const problemsApi = {
  list: (params?: {
    cursor?: string;
    limit?: number;
    pattern?: string;
    algorithm?: string;
    dataStructure?: string;
    difficulty?: string;
    search?: string;
    sort?: string;
  }) => 
    api.get<PaginatedResponse<ProblemSummaryDto>>('/problems', { params }),
  
  get: (slug: string) => 
    api.get<ProblemChapterDto>(`/problems/${slug}`),
  
  getVariations: (slug: string) => 
    api.get<ProblemChapterDto['variations']>(`/problems/${slug}/variations`),
  
  getSolutions: (slug: string) => 
    api.get<ProblemChapterDto['solutions']>(`/problems/${slug}/solutions`),
  
  getTestCases: (slug: string) => 
    api.get<ProblemChapterDto['testCases']>(`/problems/${slug}/test-cases`),
  
  getMastery: (slug: string) => 
    api.get<ProblemChapterDto['mastery']>(`/problems/${slug}/mastery`),
};

export const patternsApi = {
  list: () => 
    api.get<PatternDetailDto[]>('/patterns'),
  
  get: (slug: string) => 
    api.get<PatternDetailDto>(`/patterns/${slug}`),
};

export const algorithmsApi = {
  list: () => 
    api.get<AlgorithmDetailDto[]>('/algorithms'),
  
  get: (slug: string) => 
    api.get<AlgorithmDetailDto>(`/algorithms/${slug}`),
};

export const dataStructuresApi = {
  list: () => 
    api.get<DataStructureDetailDto[]>('/data-structures'),
  
  get: (slug: string) => 
    api.get<DataStructureDetailDto>(`/data-structures/${slug}`),
};

export const templatesApi = {
  list: (params?: { pattern?: string; language?: string }) => 
    api.get<TemplateDetailDto[]>('/templates', { params }),
  
  get: (slug: string) => 
    api.get<TemplateDetailDto>(`/templates/${slug}`),
};

export const curriculumApi = {
  getTree: () => 
    api.get<CurriculumNodeDto>('/curriculum'),
  
  getLearningPath: (problemSlug: string) => 
    api.get<LearningPathDto>(`/curriculum/path/${problemSlug}`),
};

export const executionApi = {
  submit: (request: ExecutionRequest) => 
    api.post<ExecutionJobResponse>('/execution/jobs', request),
  
  getJob: (jobId: string) => 
    api.get<ExecutionResultDto>(`/execution/jobs/${jobId}`),
  
  streamJob: (jobId: string) => {
    return new EventSource(`${api.defaults.baseURL}/execution/jobs/${jobId}/stream`);
  },

  getHistory: (problemId?: string) => 
    api.get<ExecutionResultDto[]>('/execution/jobs', { params: { problemId } }),
};

export const masteryApi = {
  getAll: (params?: { entityType?: string; status?: string }) => 
    api.get<MasteryRecordDto[]>('/mastery', { params }),
  
  getStats: () => 
    api.get<MasteryStatsDto>('/mastery/stats'),
  
  update: (entityType: string, entityId: string, data: Partial<MasteryUpdateRequest>) => 
    api.patch<MasteryRecordDto>(`/mastery/${entityType}/${entityId}`, data),

  // Review endpoints
  getReviewStats: () => 
    api.get<{ dueCount: number; overdueCount: number; learningCount: number; masteredCount: number; dailyDueNext7Days: number[] }>('/mastery/review/stats'),
  
  getDueReviews: (limit?: number) => 
    api.get<{ entityId: string; entityType: string; nextReviewDate: string; due: boolean; overdue: boolean; masteryScore: number; status: string; lastPracticed: string }[]>(`/mastery/review/due`, { params: { limit } }),
  
  getAllReviews: () => 
    api.get<{ entityId: string; entityType: string; nextReviewDate: string; due: boolean; overdue: boolean; masteryScore: number; status: string; lastPracticed: string }[]>('/mastery/review/all'),
  
  recordReview: (entityType: string, entityId: string, quality: 'AGAIN' | 'HARD' | 'GOOD' | 'EASY') => 
    api.post<{ entityId: string; entityType: string; nextReviewDate: string; due: boolean; overdue: boolean; masteryScore: number; status: string; lastPracticed: string }>(`/mastery/review/${entityType}/${entityId}`, { quality }),
};

export const aiApi = {
  startConversation: (request: AIConversationRequest) => 
    api.post<AIConversationResponse>('/ai/conversations', request),

  sendMessage: (conversationId: string, request: AIMessageRequest) => 
    api.post<AIMessageDto>(`/ai/conversations/${conversationId}/messages`, request),

  getConversation: (conversationId: string) => 
    api.get<AIMessageDto[]>(`/ai/conversations/${conversationId}/messages`),

  listConversations: (params?: { problemId?: string; cursor?: string; limit?: number }) => 
    api.get<AIConversationResponse[]>('/ai/conversations', { params }),

  streamMessage: (conversationId: string, request: AIMessageRequest, onChunk: (chunk: string) => void, onDone: () => void, onError: (error: Error) => void) => {
    const controller = new AbortController();
    
    fetch(`${api.defaults.baseURL}/ai/conversations/${conversationId}/messages/stream`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'text/event-stream',
      },
      body: JSON.stringify(request),
      signal: controller.signal,
    })
      .then(async (response) => {
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        const reader = response.body?.getReader();
        if (!reader) {
          throw new Error('No response body');
        }
        
        const decoder = new TextDecoder();
        let buffer = '';
        
        try {
          while (true) {
            const { done, value } = await reader.read();
            if (done) break;
            
            buffer += decoder.decode(value, { stream: true });
            const lines = buffer.split('\n');
            buffer = lines.pop() || '';
            
            for (const line of lines) {
              if (line.startsWith('data: ')) {
                const data = line.slice(6).trim();
                if (data === '[DONE]' || data === 'done') {
                  onDone();
                  return;
                }
                try {
                  const parsed = JSON.parse(data);
                  if (parsed.content) {
                    onChunk(parsed.content);
                  }
                } catch {
                  // If not JSON, treat as raw text
                  if (data) {
                    onChunk(data);
                  }
                }
              }
            }
          }
          onDone();
        } catch (error) {
          if (error instanceof Error && error.name !== 'AbortError') {
            onError(error);
          }
        }
      })
      .catch((error) => {
        if (error instanceof Error && error.name !== 'AbortError') {
          onError(error);
        }
      });
    
    return () => controller.abort();
  },
};

export const searchApi = {
  search: (params: { q: string; type?: string; limit?: number }) => 
    api.get<SearchResponse>('/search', { params }),
};

export default api;