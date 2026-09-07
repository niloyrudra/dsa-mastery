# DSA Mastery Platform - Architecture Document

## Overview

This document describes the architectural decisions for the Personal DSA Mastery & Coding Interview Learning Platform. The platform is designed as a local-first, single-user application that teaches Data Structures and Algorithms through pattern recognition, template-driven learning, and interactive problem-solving.

---

## High-Level Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                        Frontend (React + TypeScript)            │
│  ┌─────────────┐ ┌─────────────┐ ┌─────────────┐ ┌───────────┐  │
│  │ Curriculum  │ │  Problem    │ │   Code      │ │  AI Tutor │  │
│  │   Browser   │ │  Chapter    │ │  Playground │ │  Panel    │  │
│  └─────────────┘ └─────────────┘ └─────────────┘ └───────────┘  │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                      Backend API (Spring Boot 3.x)              │
│  ┌────────────┐ ┌────────────┐ ┌────────────┐ ┌────────────┐   │
│  │  Content   │ │ Curriculum │ │  Mastery   │ │    AI      │   │
│  │  Module    │ │  Module    │ │  Module    │ │  Module    │   │
│  └────────────┘ └────────────┘ └────────────┘ └────────────┘   │
│  ┌────────────┐ ┌────────────┐ ┌────────────┐ ┌────────────┐   │
│  │  Problem   │ │  Pattern   │ │  Template  │ │ Execution  │   │
│  │  Module    │ │  Module    │ │  Module    │ │  Module    │   │
│  └────────────┘ └────────────┘ └────────────┘ └────────────┘   │
└─────────────────────────────────────────────────────────────────┘
                              │
              ┌───────────────┼───────────────┐
              ▼               ▼               ▼
       ┌─────────────┐ ┌─────────────┐ ┌─────────────┐
       │  Database   │ │   Ollama    │ │  Code       │
       │ (PostgreSQL)│ │  (Local AI) │ │  Runner     │
       └─────────────┘ └─────────────┘ │ (Docker)    │
                                       └─────────────┘
```

---

## Technology Choices

### Backend: Spring Boot 3.x + Java 21

**Rationale:**
- Strong type system essential for domain modeling complex DSA relationships
- Excellent modular monolith support via Spring modules
- Mature ecosystem for: PostgreSQL (JPA/Hibernate), OpenAPI (SpringDoc), Testing (Testcontainers)
- Native Docker support with layered JARs
- Team familiarity and long-term maintainability
- Performance adequate for single-user local deployment

**Modules Structure:**
```
dsa-mastery-backend/
├── dsa-content/          # Problem, Pattern, Algorithm, DataStructure entities
├── dsa-curriculum/       # Curriculum graph, prerequisites, learning paths
├── dsa-problems/         # Problem chapters, variations, solutions
├── dsa-patterns/         # Pattern definitions, recognition signals, templates
├── dsa-templates/        # Code templates, annotations, mappings
├── dsa-mastery/          # Progress tracking, mastery states
├── dsa-execution/        # Code execution job management
├── dsa-ai/               # AI provider abstraction, tutor logic
└── dsa-api/              # REST controllers, DTOs, OpenAPI config
```

### Frontend: React 18 + TypeScript + Vite

**Rationale:**
- Best-in-class code editor integration (Monaco Editor)
- Strong TypeScript support for shared types with backend
- Excellent graph visualization libraries (React Flow, Cytoscape)
- Mature Markdown rendering ecosystem
- Component-driven architecture matches domain entities
- Fast HMR with Vite for development experience
- Can be served as static assets from Spring Boot in production

**Key Libraries:**
- **Monaco Editor** - Code editing with TypeScript/Python/JavaScript support
- **React Flow** - Interactive algorithm relationship graph
- **React Markdown** - Educational content rendering
- **TanStack Query** - Server state management
- **Zustand** - Client state (UI, tutor panel)
- **Tailwind CSS** - Utility-first styling for technical UI
- **Vitest + React Testing Library** - Testing

### Database: PostgreSQL 16

**Rationale:**
- Relational model fits explicit domain relationships (Problem↔Pattern, Problem↔Template, etc.)
- JSONB columns for flexible content (recognition signals, complexity explanations)
- Full-text search for problem/pattern discovery
- ACID guarantees for mastery tracking
- Local development via Docker, production-ready
- Single database avoids polyglot complexity

**Schema Approach:**
- Explicit junction tables for many-to-many relationships
- Enum types for controlled vocabularies (difficulty, mastery status, content status)
- Materialized views for curriculum graph queries
- No graph database - relationships modeled relationally

### Code Execution: Docker-based Sandbox

**Rationale:**
- Complete process isolation from host
- Resource limits (CPU, memory, pids, network) via Docker cgroups
- Language-agnostic: supports TypeScript (ts-node), JavaScript (Node), Python
- Ephemeral containers - no persistent state
- Can be extracted to separate service later
- No `eval()` or `child_process.exec()` in application process

**Architecture:**
```
POST /api/execution/jobs
     │
     ▼
┌────────────────────────────────────────┐
│ ExecutionJob Entity (queued/running/   │
│ completed/failed)                      │
└────────────────────────────────────────┘
     │
     ▼ (async via @Async or message queue)
┌────────────────────────────────────────┐
│ CodeRunnerService                      │
│  1. Create temp directory with code    │
│  2. Write test harness                 │
│  3. docker run --rm --cpus=1 --memory= │
│     256m --pids-limit=50 --network=none│
│     -v /tmp:/workspace runner-image    │
│  4. Capture stdout/stderr/exit code    │
│  5. Parse results, update job          │
└────────────────────────────────────────┘
```

**Runner Images:**
- `dsa-runner-typescript` - Node 20 + ts-node + vitest
- `dsa-runner-python` - Python 3.13 + pytest
- `dsa-runner-javascript` - Node 20 + vitest

### AI Provider Abstraction

**Rationale:**
- Local-first: Ollama as primary provider
- Cloud-ready: OpenAI-compatible, Anthropic interfaces
- Task-specific model routing (fast vs. coding vs. reasoning)
- Graceful degradation when AI unavailable

**Interface:**
```java
interface AIProvider {
    String getName();
    List<ModelInfo> getAvailableModels();
    CompletionResponse complete(CompletionRequest request);
    Stream<CompletionChunk> streamComplete(CompletionRequest request);
    EmbeddingResponse embed(EmbeddingRequest request);
}

enum ModelRole {
    FAST,       // Classification, tagging, simple explanation
    CODING,     // Code review, algorithm reasoning
    REASONING   // Content generation, difficult explanations
}
```

**Providers:**
- `OllamaProvider` - Local models (llama3.1, codellama, deepseek-coder)
- `OpenAICompatibleProvider` - OpenAI, Together, Groq, etc.
- `AnthropicProvider` - Claude models

### Content Architecture: Database-Backed with Markdown Source

**Rationale:**
- Markdown as authoring format (version-controllable, reviewable)
- Structured entities in database for querying, relationships, mastery
- Import pipeline: Markdown → Validation → Database
- Content status pipeline: DRAFT → RESEARCHED → GENERATED → VALIDATED → REVIEWED → PUBLISHED
- Frontend renders from database (not raw Markdown) for consistent UI

**Content Entities:**
- Problem, ProblemVariation, Pattern, Algorithm, DataStructure
- Template, Solution, ComplexityAnalysis, TestCase
- Concept, Reference, LearningObjective, Prerequisite
- CurriculumNode, Relationship, MasteryRecord

---

## Modular Monolith Design

**Why Not Microservices:**
- Single user, local deployment
- Domain boundaries clear but data highly interconnected
- Operational complexity unjustified
- Can extract modules later if needed

**Module Boundaries:**
- Each module = Spring `@Configuration` + JPA entities + Repository + Service
- Shared kernel: `dsa-common` (enums, exceptions, base entities)
- Modules communicate via domain events (Spring ApplicationEventPublisher)
- No cyclic dependencies (enforced by ArchUnit tests)

---

## API Design

**RESTful with OpenAPI 3.1:**
- Resource-oriented URLs: `/api/problems/{id}`, `/api/patterns/{slug}`
- DTOs separate from entities
- Cursor-based pagination for lists
- Filtering: `?pattern=two-pointer&difficulty=medium`
- Problem search: `GET /api/problems/search?q=two+sum`
- Execution: `POST /api/execution/jobs` (async), `GET /api/execution/jobs/{id}`

**Error Handling:**
- RFC 7807 Problem Details
- Global `@ControllerAdvice` for validation, not found, execution errors

---

## Security (Local-First)

- No authentication in V1 (local-only)
- Code execution: Docker isolation, no network, resource limits, read-only filesystem
- No secrets in runner containers
- Database: local Docker, no external exposure
- AI: local Ollama preferred, cloud keys in `.env` only

---

## Observability

- Structured logging (Logback + JSON)
- Health endpoint: `GET /actuator/health`
- Execution job logging (job ID, language, duration, result)
- Error tracking: Sentry optional for future

---

## Deployment

**Development:**
```bash
docker compose up -d  # postgres, ollama
./gradlew bootRun     # backend
npm run dev           # frontend
```

**Production (Future):**
- Backend: Spring Boot JAR in container
- Frontend: Static files served by backend or CDN
- Database: Managed PostgreSQL
- AI: Ollama on GPU instance or cloud provider
- Code Runner: Separate container fleet or cloud function

---

## Architecture Decision Records

See `docs/architecture-decisions/` for individual ADRs:
- ADR-001: Spring Boot Modular Monolith
- ADR-002: PostgreSQL for Domain Relationships
- ADR-003: React + TypeScript + Vite Frontend
- ADR-004: Docker Sandbox for Code Execution
- ADR-005: AI Provider Abstraction with Ollama Primary
- ADR-006: Markdown-to-Database Content Pipeline