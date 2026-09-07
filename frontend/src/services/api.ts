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
  
  getHistory: (problemId?: string) => 
    api.get<ExecutionResultDto[]>('/execution/history', { params: { problemId } }),
};

export const masteryApi = {
  getAll: (params?: { entityType?: string; status?: string }) => 
    api.get<MasteryRecordDto[]>('/mastery', { params }),
  
  getStats: () => 
    api.get<MasteryStatsDto>('/mastery/stats'),
  
  update: (entityType: string, entityId: string, data: MasteryUpdateRequest) => 
    api.patch<MasteryRecordDto>(`/mastery/${entityType}/${entityId}`, data),
};

export const aiApi = {
  startConversation: (request: AIConversationRequest) => 
    api.post<AIConversationResponse>('/ai/conversations', request),
  
  sendMessage: (conversationId: string, request: AIMessageRequest) => 
    api.post<AIMessageDto>(`/ai/conversations/${conversationId}/messages`, request, {
      responseType: 'stream',
    }),
  
  getConversation: (conversationId: string) => 
    api.get<{ messages: AIMessageDto[] }>(`/ai/conversations/${conversationId}`),
  
  listConversations: (params?: { problemId?: string; cursor?: string; limit?: number }) => 
    api.get<AIConversationResponse[]>('/ai/conversations', { params }),
};

export const searchApi = {
  search: (params: { q: string; type?: string; limit?: number }) => 
    api.get<SearchResponse>('/search', { params }),
};

export default api;