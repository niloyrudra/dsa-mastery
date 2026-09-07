# DSA Mastery Platform

A personal Data Structures & Algorithms learning platform designed to teach problem-solving patterns and transferable reasoning for coding interviews.

## Features

- **Structured Curriculum** - 150 curated problems organized by patterns, algorithms, and data structures
- **Pattern-First Learning** - Learn to recognize signals, understand invariants, and apply reusable templates
- **Worst → Better → Best** - Progressive solution approach teaching the reasoning behind optimizations
- **Interactive Code Playground** - TypeScript, JavaScript, Python support with Monaco Editor
- **Secure Code Execution** - Docker-sandboxed runner with resource limits
- **AI Tutor** - Socratic guidance, progressive hints, code review, and misconception detection
- **Mastery Tracking** - Transparent progress tracking per problem, pattern, and concept
- **Local-First** - Runs entirely offline with Ollama, no cloud dependencies required

## Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                      Frontend (React + TS)                  │
│  Curriculum • Problem Chapter • Code Editor • AI Tutor     │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                     Backend (Spring Boot 3)                 │
│  Content • Curriculum • Problems • Patterns • Templates    │
│  Mastery • Execution • AI Integration                       │
└─────────────────────────────────────────────────────────────┘
                              │
              ┌───────────────┼───────────────┐
              ▼               ▼               ▼
        ┌───────────┐   ┌───────────┐   ┌───────────┐
        │PostgreSQL │   │  Ollama   │   │  Docker   │
        │  (Data)   │   │  (Local AI)│  │ (Sandbox) │
        └───────────┘   └───────────┘   └───────────┘
```

## Quick Start

### Prerequisites

- Docker & Docker Compose
- Java 21 (for local backend development)
- Node 20 (for local frontend development)
- Ollama (for local AI)

### Using Docker Compose (Recommended)

```bash
# Clone and navigate
cd dsa-mastery

# Copy environment template
cp .env.example .env

# Start all services
docker compose up -d

# Access the application
# Frontend: http://localhost:3000
# Backend API: http://localhost:8080/api/v1
# Swagger UI: http://localhost:8080/docs
# Ollama: http://localhost:11434
```

### Local Development

**Backend:**
```bash
cd backend
# Requires Java 21 (Gradle toolchain is pinned to 21; wrapper is pinned to Gradle 8.5, matching the Docker image)
./gradlew :dsa-api:bootRun
```

> **Note:** Start PostgreSQL and Ollama first (see below). On startup the API validates the schema (JPA `ddl-auto: validate`) and runs Flyway migrations from `dsa-api/src/main/resources/db/migration`.

**Frontend:**
```bash
cd frontend
npm install
npm run dev
```

**Database:**
```bash
docker run -d --name postgres \
  -e POSTGRES_DB=dsa_mastery \
  -e POSTGRES_USER=dsa_user \
  -e POSTGRES_PASSWORD=dsa_password \
  -p 5432:5432 \
  postgres:16-alpine
```

**Ollama (for AI):**
```bash
# Install Ollama from https://ollama.ai
# Pull recommended models
ollama pull llama3.1:8b      # Fast model
ollama pull codellama:13b    # Coding model
ollama pull deepseek-coder:33b # Reasoning model
```

## Project Structure

```
dsa-mastery/
├── backend/                 # Spring Boot modular monolith
│   ├── dsa-api/            # REST controllers, DTOs, OpenAPI
│   ├── dsa-common/         # Shared enums, base entities
│   ├── dsa-content/        # Problem, Pattern, Algorithm entities
│   ├── dsa-curriculum/     # Curriculum graph, learning paths
│   ├── dsa-problems/       # Problem chapters, variations
│   ├── dsa-patterns/       # Pattern definitions, templates
│   ├── dsa-templates/      # Code templates, mappings
│   ├── dsa-algorithms/     # Algorithm definitions
│   ├── dsa-data-structures/ # Data structure definitions
│   ├── dsa-mastery/        # Progress tracking
│   ├── dsa-execution/      # Code execution sandbox
│   └── dsa-ai/             # AI provider abstraction
├── frontend/               # React + TypeScript + Vite
│   ├── src/
│   │   ├── components/     # UI components
│   │   ├── pages/          # Route pages
│   │   ├── hooks/          # React Query hooks
│   │   ├── services/       # API client
│   │   ├── store/          # Zustand state
│   │   └── types/          # TypeScript types
│   └── public/
├── docs/                   # Architecture documentation
└── docker-compose.yml
```

## Content Authoring

The content model (problems, patterns, algorithms, data structures, templates, solutions, test cases) is documented in detail in `docs/content-model.md` and `docs/database-design.md`. The initial schema and seed content ship as Flyway migrations under `backend/dsa-api/src/main/resources/db/migration/` and are applied automatically on API startup.

A Markdown-based authoring pipeline (a `content/` directory plus a `contentImport` Gradle task, as described in `docs/content-model.md` and `docs/architecture.md`) is **designed but not yet wired into the build**. Until it lands, content is managed directly through the schema migrations.

## API Documentation

OpenAPI/Swagger UI available at `http://localhost:8080/docs`

Key endpoints:
- `GET /api/v1/problems` - List problems with filters
- `GET /api/v1/problems/{slug}` - Full problem chapter
- `GET /api/v1/patterns/{slug}` - Pattern detail
- `GET /api/v1/templates/{slug}` - Template with mappings
- `POST /api/v1/execution/jobs` - Submit code for execution
- `GET /api/v1/mastery/stats` - Progress statistics
- `POST /api/v1/ai/conversations` - Start AI tutor session

## Security

- Code execution runs in isolated Docker containers
- No network access for user code
- Resource limits: 1 CPU, 256MB RAM, 30s timeout
- Read-only filesystem, non-root user
- No `eval()` or direct process execution in application

## Extending the Platform

### Adding a New Problem

1. Create problem directory under `content/problems/`
2. Write `problem.md` with frontmatter
3. Add solution files in `solutions/`
3. Define `test-cases.yaml`
4. Create templates in `templates/`
5. Add `references.yaml`
6. Run import pipeline

### Adding a New Pattern

1. Create pattern directory under `content/patterns/`
2. Write `pattern.md` with full pedagogical structure
3. Add template files in `templates/`
4. Add `references.yaml`
5. Link problems in curriculum

## License

MIT License - See LICENSE file for details.