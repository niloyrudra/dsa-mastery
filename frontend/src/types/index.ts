export type Difficulty = 'EASY' | 'MEDIUM' | 'HARD';
export type ContentStatus = 'DRAFT' | 'RESEARCHED' | 'GENERATED' | 'VALIDATED' | 'REVIEWED' | 'PUBLISHED';
export type MasteryStatus = 'NOT_STARTED' | 'LEARNING' | 'PRACTICING' | 'SOLVED' | 'MASTERED' | 'NEEDS_REVIEW';
export type TestCaseType = 'BASIC' | 'EDGE_CASE' | 'PERFORMANCE' | 'CUSTOM';
export type ExecutionStatus = 'QUEUED' | 'COMPILING' | 'RUNNING' | 'PASSED' | 'FAILED' | 'ERROR' | 'TIMEOUT';
export type NodeType = 'CATEGORY' | 'PATTERN' | 'ALGORITHM' | 'PROBLEM';
export type RelationshipType = 'USES' | 'REQUIRES' | 'VARIATION_OF' | 'RELATED_TO' | 'PREREQUISITE_OF' | 'IMPLEMENTS';
export type ReferenceType = 'OFFICIAL_DOCS' | 'PRIMARY_SOURCE' | 'UNIVERSITY' | 'BOOK' | 'TECH_RESOURCE' | 'PROBLEM_SOURCE';
export type Language = 'TYPESCRIPT' | 'JAVASCRIPT' | 'PYTHON';
export type Approach = 'WORST' | 'BETTER' | 'BEST';
export type BloomLevel = 'REMEMBER' | 'UNDERSTAND' | 'APPLY' | 'ANALYZE' | 'EVALUATE' | 'CREATE';
export type ModelRole = 'FAST' | 'CODING' | 'REASONING';
export type MessageRole = 'USER' | 'ASSISTANT' | 'SYSTEM';
export type MessageType = 'QUESTION' | 'HINT' | 'EXPLANATION' | 'CODE_REVIEW' | 'COMPLEXITY_ANALYSIS' | 'VARIATION';

export interface UUID {
  value: string;
}

export interface ComplexityDto {
  timeComplexity: string;
  spaceComplexity: string;
  timeReasoning: string;
  spaceReasoning: string;
  bestCaseTime?: string;
  worstCaseTime?: string;
  averageCaseTime?: string;
}

export interface ProblemSummaryDto {
  id: string;
  slug: string;
  title: string;
  difficulty: Difficulty;
  patterns: string[];
  algorithms: string[];
  dataStructures: string[];
  orderIndex: number;
  masteryStatus: MasteryStatus;
}

export interface ProblemChapterDto {
  id: string;
  slug: string;
  title: string;
  description: string;
  externalUrl: string;
  difficulty: Difficulty;
  patterns: PatternSummaryDto[];
  algorithms: AlgorithmSummaryDto[];
  dataStructures: DataStructureSummaryDto[];
  templates: TemplateSummaryDto[];
  variations: ProblemVariationDto[];
  solutions: SolutionDto[];
  testCases: TestCaseDto[];
  learningObjectives: LearningObjectiveDto[];
  references: ReferenceDto[];
  relatedProblems: ProblemSummaryDto[];
  prerequisites: ConceptSummaryDto[];
  mastery: MasteryDto;
}

export interface PatternSummaryDto {
  id: string;
  slug: string;
  name: string;
}

export interface AlgorithmSummaryDto {
  id: string;
  slug: string;
  name: string;
}

export interface DataStructureSummaryDto {
  id: string;
  slug: string;
  name: string;
}

export interface TemplateSummaryDto {
  id: string;
  slug: string;
  name: string;
}

export interface ProblemVariationDto {
  id: string;
  name: string;
  description: string;
}

export interface SolutionDto {
  id: string;
  name: string;
  approach: Approach;
  explanation: string;
  whyItWorks: string;
  whyNotIdeal?: string;
  bottleneckRemaining?: string;
  insightToNext?: string;
  complexity: ComplexityDto;
  language: Language;
  code: string;
}

export interface TestCaseDto {
  id: string;
  name: string;
  input: unknown;
  expectedOutput: unknown;
  description?: string;
  type: TestCaseType;
  isHidden: boolean;
}

export interface LearningObjectiveDto {
  id: string;
  description: string;
  bloomLevel: string;
}

export interface ReferenceDto {
  id: string;
  title: string;
  url: string;
  type: string;
  relevantConcept?: string;
  verifies?: string;
}

export interface ConceptSummaryDto {
  id: string;
  slug: string;
  name: string;
}

export interface MasteryDto {
  status: MasteryStatus;
  score: number;
  viewed: boolean;
  explanationViewed: boolean;
  templateViewed: boolean;
  attempts: number;
  passedTests: boolean;
  solutionRevealed: boolean;
  optimalUnderstood: boolean;
  variationsAttempted: boolean;
}

export interface PatternDetailDto {
  id: string;
  slug: string;
  name: string;
  category?: string;
  definition: string;
  whenToUse: string;
  whyItWorks: string;
  intuition?: string;
  invariant?: string;
  strengths?: string;
  weaknesses?: string;
  recognitionSignals: string[];
  commonFailureModes?: string;
  generalizations?: string;
  complexity: ComplexityDto;
  status: ContentStatus;
  templates: TemplateSummaryDto[];
  algorithms: AlgorithmSummaryDto[];
  dataStructures: DataStructureSummaryDto[];
  problems: ProblemSummaryDto[];
  subPatterns: PatternSummaryDto[];
  relatedPatterns: PatternSummaryDto[];
}

export interface AlgorithmDetailDto {
  id: string;
  slug: string;
  name: string;
  description: string;
  stepByStepReasoning: string;
  complexityAnalysis: string;
  complexity: ComplexityDto;
  status: ContentStatus;
  patterns: PatternSummaryDto[];
  dataStructures: DataStructureSummaryDto[];
  templates: TemplateSummaryDto[];
  problems: ProblemSummaryDto[];
  references: ReferenceDto[];
}

export interface DataStructureDetailDto {
  id: string;
  slug: string;
  name: string;
  description: string;
  operations: Record<string, string>;
  whyUsefulHere?: string;
  tradeoffs?: string;
  alternatives?: string;
  status: ContentStatus;
  problems: ProblemSummaryDto[];
  patterns: PatternSummaryDto[];
  algorithms: AlgorithmSummaryDto[];
}

export interface TemplateDetailDto {
  id: string;
  slug: string;
  name: string;
  language: Language;
  purpose: string;
  whenToReachForIt: string;
  prerequisites?: string;
  mentalModel: string;
  code: string;
  explanation: string;
  invariant: string;
  commonModifications?: string;
  commonMistakes?: string;
  complexity: ComplexityDto;
  status: ContentStatus;
  pattern: PatternSummaryDto;
  exampleProblems: ProblemSummaryDto[];
  mappings: TemplateMappingDto[];
}

export interface TemplateMappingDto {
  templateMarker: number;
  solutionCodeSnippet: string;
  explanation: string;
}

export interface CurriculumNodeDto {
  id: string;
  name: string;
  description?: string;
  type: NodeType;
  orderIndex: number;
  depth: number;
  problemId?: string;
  patternId?: string;
  algorithmId?: string;
  children: CurriculumNodeDto[];
}

export interface LearningPathDto {
  targetProblem: string;
  prerequisites: PrerequisiteDto[];
  nextProblems: NextProblemDto[];
}

export interface PrerequisiteDto {
  slug: string;
  name: string;
  type: string;
  masteryStatus: string;
}

export interface NextProblemDto {
  slug: string;
  title: string;
  difficulty: string;
}

export interface ExecutionRequest {
  problemId: string;
  language: Language;
  code: string;
  testCases?: string[];
}

export interface ExecutionJobResponse {
  jobId: string;
  status: ExecutionStatus;
  submittedAt: string;
}

export interface ExecutionResultDto {
  jobId: string;
  status: ExecutionStatus;
  passedTests: number;
  totalTests: number;
  executionTimeMs?: number;
  memoryBytes?: number;
  output?: string;
  error?: string;
  testResults: TestResultDto[];
  completedAt?: string;
}

export interface TestResultDto {
  name: string;
  passed: boolean;
  input: string;
  expected: string;
  actual: string;
  durationMs?: number;
}

export interface MasteryRecordDto {
  entityId: string;
  entityType: string;
  status: MasteryStatus;
  score: number;
  viewed: boolean;
  explanationViewed: boolean;
  templateViewed: boolean;
  attempts: number;
  passedTests: boolean;
  solutionRevealed: boolean;
  optimalUnderstood: boolean;
  variationsAttempted: boolean;
  lastPracticed?: string;
  masteredAt?: string;
  problemsSolved: number;
  problemsAttempted: number;
  conceptProficiency: number;
}

export interface MasteryStatsDto {
  problems: ProblemStats;
  patterns: Record<string, number>;
  overallProgress: number;
}

export interface ProblemStats {
  total: number;
  mastered: number;
  learning: number;
  notStarted: number;
}

export interface AIConversationRequest {
  problemId?: string;
  modelRole: ModelRole;
}

export interface AIConversationResponse {
  conversationId: string;
  modelUsed: string;
  modelRole: ModelRole;
}

export interface AIMessageRequest {
  content: string;
  type: MessageType;
}

export interface AIMessageDto {
  id: string;
  role: MessageRole;
  content: string;
  type: MessageType;
  sequence: number;
  createdAt: string;
}

export interface AIStreamChunk {
  type: string;
  content: string;
  messageId?: string;
}

export interface SearchRequest {
  q: string;
  type?: string;
  limit?: number;
}

export interface SearchResultDto {
  type: string;
  id: string;
  slug: string;
  title: string;
  snippet: string;
  difficulty?: string;
}

export interface SearchResponse {
  results: SearchResultDto[];
}

export interface PaginatedResponse<T> {
  data: T[];
  nextCursor?: string;
  hasMore: boolean;
  total: number;
}