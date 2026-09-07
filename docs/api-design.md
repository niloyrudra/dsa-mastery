# DSA Mastery Platform - API Design

## Base Configuration

- **Base URL**: `http://localhost:8080/api/v1`
- **Content-Type**: `application/json`
- **OpenAPI Spec**: `GET /api/v1/docs` (Swagger UI), `GET /v3/api-docs` (JSON)

## Error Handling (RFC 7807)

```json
{
  "type": "https://dsa-mastery.dev/errors/validation-error",
  "title": "Validation Failed",
  "status": 400,
  "detail": "Request validation failed",
  "instance": "/api/v1/problems",
  "errors": [
    { "field": "slug", "message": "must be unique" }
  ]
}
```

---

## Problems API

### List Problems
```
GET /api/v1/problems
```

**Query Parameters:**
| Param | Type | Description |
|-------|------|-------------|
| `cursor` | string | Opaque cursor for pagination |
| `limit` | int (1-100) | Page size (default: 20) |
| `pattern` | string | Filter by pattern slug |
| `algorithm` | string | Filter by algorithm slug |
| `dataStructure` | string | Filter by data structure slug |
| `difficulty` | string | EASY, MEDIUM, HARD |
| `status` | string | Content status (default: PUBLISHED) |
| `search` | string | Full-text search on title/description |
| `sort` | string | `order`, `-order`, `difficulty`, `title` |

**Response:**
```json
{
  "data": [
    {
      "id": "uuid",
      "slug": "two-sum",
      "title": "Two Sum",
      "difficulty": "EASY",
      "patterns": ["two-pointer", "hash-map"],
      "algorithms": ["hash-map-lookup"],
      "dataStructures": ["hash-map"],
      "orderIndex": 1,
      "masteryStatus": "NOT_STARTED"
    }
  ],
  "pagination": {
    "nextCursor": "opaque-cursor",
    "hasMore": true,
    "total": 150
  }
}
```

### Get Problem Chapter
```
GET /api/v1/problems/{slug}
```

**Response:**
```json
{
  "id": "uuid",
  "slug": "two-sum",
  "title": "Two Sum",
  "description": "Original educational description...",
  "externalUrl": "https://leetcode.com/problems/two-sum/",
  "difficulty": "EASY",
  "status": "PUBLISHED",
  "orderIndex": 1,
  "patterns": [
    { "id": "uuid", "slug": "two-pointer", "name": "Two Pointer" }
  ],
  "algorithms": [
    { "id": "uuid", "slug": "hash-map-lookup", "name": "Hash Map Lookup" }
  ],
  "dataStructures": [
    { "id": "uuid", "slug": "hash-map", "name": "Hash Map" }
  ],
  "templates": [
    { "id": "uuid", "slug": "two-pointer-opposite", "name": "Two Pointer - Opposite Ends" }
  ],
  "variations": [
    { "id": "uuid", "name": "Sorted Input", "description": "Array is pre-sorted" }
  ],
  "solutions": [
    {
      "id": "uuid",
      "name": "Brute Force",
      "approach": "WORST",
      "explanation": "...",
      "complexity": { "time": "O(n²)", "space": "O(1)", "timeReasoning": "...", "spaceReasoning": "..." },
      "language": "TYPESCRIPT",
      "code": "function twoSum..."
    }
  ],
  "testCases": [
    { "id": "uuid", "name": "Basic", "input": "[2,7,11,15], 9", "expectedOutput": "[0,1]", "type": "BASIC" }
  ],
  "learningObjectives": [
    { "description": "Recognize complement pairs", "bloomLevel": "APPLY" }
  ],
  "references": [
    { "title": "Hash Tables", "url": "https://...", "type": "TECH_RESOURCE", "verifies": "O(1) average lookup" }
  ],
  "prerequisites": [
    { "id": "uuid", "slug": "arrays", "name": "Arrays" }
  ],
  "relatedProblems": [
    { "id": "uuid", "slug": "three-sum", "title": "3Sum" }
  ],
  "mastery": {
    "status": "LEARNING",
    "score": 25,
    "viewed": true,
    "attempts": 2
  }
}
```

### Get Problem Variations
```
GET /api/v1/problems/{slug}/variations
```

### Get Problem Solutions
```
GET /api/v1/problems/{slug}/solutions
```

### Get Problem Test Cases
```
GET /api/v1/problems/{slug}/test-cases
```

### Get Problem Mastery
```
GET /api/v1/problems/{slug}/mastery
```

---

## Patterns API

### List Patterns
```
GET /api/v1/patterns
```

**Query Parameters:** `cursor`, `limit`, `search`, `sort`

**Response:**
```json
{
  "data": [
    {
      "id": "uuid",
      "slug": "two-pointer",
      "name": "Two Pointer",
      "definition": "...",
      "whenToUse": "...",
      "recognitionSignals": ["sorted array", "pair with target"],
      "complexity": { "time": "O(n)", "space": "O(1)" },
      "templateCount": 3,
      "problemCount": 12
    }
  ]
}
```

### Get Pattern Detail
```
GET /api/v1/patterns/{slug}
```

**Response:**
```json
{
  "id": "uuid",
  "slug": "two-pointer",
  "name": "Two Pointer",
  "definition": "...",
  "whenToUse": "...",
  "whyItWorks": "...",
  "intuition": "...",
  "invariant": "...",
  "strengths": "...",
  "weaknesses": "...",
  "recognitionSignals": ["sorted array", "pair with target", "..."],
  "commonFailureModes": "...",
  "generalizations": "...",
  "complexity": { "time": "O(n)", "space": "O(1)", "timeReasoning": "...", "spaceReasoning": "..." },
  "templates": [
    { "id": "uuid", "slug": "two-pointer-opposite", "name": "Opposite Ends" },
    { "id": "uuid", "slug": "two-pointer-same", "name": "Same Direction" }
  ],
  "algorithms": [...],
  "dataStructures": [...],
  "problems": [
    { "id": "uuid", "slug": "two-sum", "title": "Two Sum", "difficulty": "EASY" }
  ],
  "subPatterns": [...],
  "relatedPatterns": [...]
}
```

---

## Algorithms API

### List Algorithms
```
GET /api/v1/algorithms
```

### Get Algorithm Detail
```
GET /api/v1/algorithms/{slug}
```

---

## Data Structures API

### List Data Structures
```
GET /api/v1/data-structures
```

### Get Data Structure Detail
```
GET /api/v1/data-structures/{slug}
```

---

## Templates API

### List Templates
```
GET /api/v1/templates
```

**Query Parameters:** `pattern` (pattern slug), `language`

### Get Template Detail
```
GET /api/v1/templates/{slug}
```

**Response:**
```json
{
  "id": "uuid",
  "slug": "two-pointer-opposite",
  "name": "Two Pointer - Opposite Ends",
  "language": "TYPESCRIPT",
  "purpose": "...",
  "whenToReachForIt": "...",
  "prerequisites": "...",
  "mentalModel": "...",
  "code": "function twoPointer(arr, target) { [1] let left = 0; [2] let right = arr.length - 1; [3] while (left < right) { ... } }",
  "explanation": "[1] Initialize left pointer at start\n[2] Initialize right pointer at end\n[3] Maintain invariant: all pairs outside [left, right] checked",
  "invariant": "Sum of elements outside window has been evaluated",
  "commonModifications": "...",
  "commonMistakes": "...",
  "complexity": { "time": "O(n)", "space": "O(1)", "timeReasoning": "...", "spaceReasoning": "..." },
  "exampleProblems": [
    { "id": "uuid", "slug": "two-sum", "title": "Two Sum" }
  ]
}
```

---

## Curriculum API

### Get Curriculum Tree
```
GET /api/v1/curriculum
```

**Response:**
```json
{
  "id": "uuid",
  "name": "DSA Mastery Curriculum",
  "type": "CATEGORY",
  "children": [
    {
      "id": "uuid",
      "name": "Arrays & Hashing",
      "type": "CATEGORY",
      "children": [
        {
          "id": "uuid",
          "name": "Two Pointer",
          "type": "PATTERN",
          "patternId": "uuid",
          "children": [
            { "id": "uuid", "name": "Two Sum", "type": "PROBLEM", "problemId": "uuid", "difficulty": "EASY" }
          ]
        }
      ]
    }
  ]
}
```

### Get Learning Path (Prerequisites)
```
GET /api/v1/curriculum/path/{problemSlug}
```

**Response:**
```json
{
  "targetProblem": "two-sum",
  "prerequisites": [
    { "slug": "arrays", "name": "Arrays", "type": "CONCEPT", "masteryStatus": "MASTERED" },
    { "slug": "hash-map", "name": "Hash Map", "type": "DATA_STRUCTURE", "masteryStatus": "LEARNING" }
  ],
  "nextProblems": [
    { "slug": "three-sum", "title": "3Sum", "difficulty": "MEDIUM" }
  ]
}
```

---

## Mastery API

### Get All Mastery Records
```
GET /api/v1/mastery
```

**Query Parameters:** `entityType` (PROBLEM, PATTERN, ALGORITHM, CONCEPT), `status`

### Update Mastery (Progress Tracking)
```
PATCH /api/v1/mastery/{entityType}/{entityId}
```

**Request:**
```json
{
  "viewed": true,
  "explanationViewed": true,
  "templateViewed": true,
  "attempts": 3,
  "passedTests": true,
  "solutionRevealed": false,
  "optimalUnderstood": true,
  "variationsAttempted": true
}
```

### Get Mastery Statistics
```
GET /api/v1/mastery/stats
```

**Response:**
```json
{
  "problems": { "total": 150, "mastered": 45, "learning": 30, "notStarted": 75 },
  "patterns": { "two-pointer": 80, "sliding-window": 60, "binary-search": 75 },
  "overallProgress": 42
}
```

---

## Code Execution API

### Submit Code for Execution
```
POST /api/v1/execution/jobs
```

**Request:**
```json
{
  "problemId": "uuid",
  "language": "TYPESCRIPT",
  "code": "function twoSum(nums, target) { ... }",
  "testCases": ["basic", "edge-case"]  // optional, defaults to all non-hidden
}
```

**Response:**
```json
{
  "jobId": "uuid",
  "status": "QUEUED",
  "submittedAt": "2026-01-15T10:30:00Z"
}
```

### Get Execution Job Status
```
GET /api/v1/execution/jobs/{jobId}
```

**Response:**
```json
{
  "jobId": "uuid",
  "status": "PASSED",
  "passedTests": 15,
  "totalTests": 15,
  "executionTimeMs": 42,
  "memoryBytes": 1048576,
  "output": "All tests passed",
  "error": null,
  "testResults": [
    { "name": "Basic", "passed": true, "input": "...", "expected": "...", "actual": "..." },
    { "name": "Edge: Empty Array", "passed": true, "input": "...,", "expected": "...", "actual": "..." }
  ],
  "completedAt": "2026-01-15T10:30:01Z"
}
```

### Get Execution History
```
GET /api/v1/execution/history
```

**Query Parameters:** `problemId`, `status`, `cursor`, `limit`

---

## AI Tutor API

### Start Conversation
```
POST /api/v1/ai/conversations
```

**Request:**
```json
{
  "problemId": "uuid",  // optional
  "modelRole": "CODING" // FAST, CODING, REASONING
}
```

**Response:**
```json
{
  "conversationId": "uuid",
  "modelUsed": "codellama:13b",
  "modelRole": "CODING"
}
```

### Send Message
```
POST /api/v1/ai/conversations/{conversationId}/messages
```

**Request:**
```json
{
  "content": "Why does the two-pointer approach work here?",
  "type": "QUESTION"
}
```

**Response (Streaming - SSE):**
```
data: {"type": "chunk", "content": "The two-pointer approach works because..."}
data: {"type": "chunk", "content": " the array is sorted..."}
data: {"type": "done", "messageId": "uuid"}
```

### Get Conversation History
```
GET /api/v1/ai/conversations/{conversationId}
```

### List Conversations
```
GET /api/v1/ai/conversations
```

**Query Parameters:** `problemId`, `cursor`, `limit`

---

## Search API

### Global Search
```
GET /api/v1/search
```

**Query Parameters:**
| Param | Type | Description |
|-------|------|-------------|
| `q` | string | Search query |
| `type` | string | PROBLEM, PATTERN, ALGORITHM, DATA_STRUCTURE, TEMPLATE, CONCEPT |
| `limit` | int | Max results (default: 10) |

**Response:**
```json
{
  "results": [
    {
      "type": "PROBLEM",
      "id": "uuid",
      "slug": "two-sum",
      "title": "Two Sum",
      "snippet": "Find two numbers that add up to target...",
      "difficulty": "EASY"
    }
  ]
}
```

---

## References API

### Get References for Problem
```
GET /api/v1/problems/{slug}/references
```

### Get References for Pattern
```
GET /api/v1/patterns/{slug}/references
```

---

## Health & Info

### Health Check
```
GET /actuator/health
```

### Application Info
```
GET /actuator/info
```

---

## Rate Limiting (Future)

- Not implemented in V1
- Headers reserved: `X-RateLimit-Limit`, `X-RateLimit-Remaining`, `X-RateLimit-Reset`

---

## Versioning

- URL versioning: `/api/v1/`
- Breaking changes → new version
- Deprecation: 6-month notice via `Deprecation` header

---

## DTOs (Shared Types)

See `docs/api-dtos.md` for complete TypeScript/Java DTO definitions.