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

- Docker & Docker Compose (v2.x)
- Java 21 (for local backend development)
- Node 20 (for local frontend development)
- Ollama (for local AI) - [Install from ollama.ai](https://ollama.ai)

### Using Docker Compose (Recommended)

```bash
# Clone and navigate
cd dsa-mastery

# Copy environment template (optional - defaults work out of the box)
cp .env.example .env

# Start all services
docker compose up -d

# Check service health
docker compose ps

# View logs
docker compose logs -f backend
```

**Access the application:**
- Frontend: http://localhost:3000
- Backend API: http://localhost:8080/api/v1
- Swagger UI: http://localhost:8080/docs
- OpenAPI Spec: http://localhost:8080/v3/api-docs
- Ollama: http://localhost:11434
- Actuator Health: http://localhost:8080/actuator/health

**Pull recommended Ollama models (optional, for AI features):**
```bash
ollama pull llama3.1:8b      # Fast model
ollama pull codellama:13b    # Coding model
ollama pull deepseek-coder:33b # Reasoning model
```

### Local Development

#### Backend (Spring Boot)

```bash
cd backend

# Set Java 21 (adjust path for your system)
# Windows PowerShell:
$env:JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-21.0.12.101-hotspot"
$env:PATH = "$env:JAVA_HOME\bin;$env:PATH"

# Linux/macOS:
export JAVA_HOME=/path/to/jdk-21
export PATH=$JAVA_HOME/bin:$PATH

# Run API module (requires PostgreSQL and Ollama running)
./gradlew :dsa-api:bootRun

# Run tests
./gradlew test

# Build JAR
./gradlew :dsa-api:bootJar
```

**Required external services for local backend:**
```bash
# PostgreSQL
docker run -d --name postgres \
  -e POSTGRES_DB=dsa_mastery \
  -e POSTGRES_USER=dsa_user \
  -e POSTGRES_PASSWORD=dsa_password \
  -p 5432:5432 \
  postgres:16-alpine

# Ollama (install first from https://ollama.ai)
ollama serve
```

> **Note:** On startup the API validates the schema (JPA `ddl-auto: validate`) and runs Flyway migrations from `dsa-api/src/main/resources/db/migration`. The database must be empty or match the migration state.

#### Frontend (React + Vite)

```bash
cd frontend

# Install dependencies
npm install

# Start dev server (proxies API calls to localhost:8080)
npm run dev

# Build for production
npm run build

# Preview production build
npm run preview

# Run linting
npm run lint

# Run type checking
npm run typecheck
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
│   ├── dsa-mastery/        # Progress tracking & spaced repetition
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
├── content/                # Markdown-based content authoring
│   ├── curriculum.yaml     # Curriculum structure
│   ├── problems/           # Problem definitions (problem.md, solutions/, templates/, test-cases.yaml, references.yaml)
│   └── patterns/           # Pattern definitions (pattern.md, references.yaml)
├── docs/                   # Architecture documentation
└── docker-compose.yml
```

## Content Authoring

The content model (problems, patterns, algorithms, data structures, templates, solutions, test cases) is documented in detail in `docs/content-model.md` and `docs/database-design.md`. The initial schema and seed content ship as Flyway migrations under `backend/dsa-api/src/main/resources/db/migration/` and are applied automatically on API startup.

A Markdown-based authoring pipeline (a `content/` directory plus a `contentImport` Gradle task, as described in `docs/content-model.md` and `docs/architecture.md`) is **designed but not yet wired into the build**. Until it lands, content is managed directly through the schema migrations.

### Current Content
The `content/` directory contains:
- 6 example problems (two-sum, valid-palindrome, maximum-depth-of-binary-tree, search-insert-position, container-with-most-water, number-of-islands)
- 7 patterns (two-pointer, sliding-window-variable, hash-map-lookup, binary-search-boundary, tree-dfs, tree-bfs, graph-dfs, graph-bfs, graph-union-find)
- Curriculum structure in `curriculum.yaml`

### Adding Content Manually (via Flyway)
1. Create a new migration file: `V6__add_my_content.sql` in `dsa-api/src/main/resources/db/migration/`
2. Insert problems, patterns, templates, solutions, test cases
3. Restart backend to apply

### Using Admin Import Endpoint
```bash
# Import content from content/ directory (when implemented)
curl -X POST http://localhost:8080/api/v1/admin/content/import \
  -H "Content-Type: application/json" \
  -d '{"contentRoot": "content"}'
```

## API Documentation

OpenAPI/Swagger UI available at `http://localhost:8080/docs`

Key endpoints:
- `GET /api/v1/problems` - List problems with filters
- `GET /api/v1/problems/{slug}` - Full problem chapter
- `GET /api/v1/patterns/{slug}` - Pattern detail
- `GET /api/v1/templates/{slug}` - Template with mappings
- `POST /api/v1/execution/jobs` - Submit code for execution
- `GET /api/v1/mastery/stats` - Progress statistics
- `GET /api/v1/mastery/review/stats` - Spaced repetition stats
- `GET /api/v1/mastery/review/due` - Items due for review
- `POST /api/v1/mastery/review/{entityType}/{entityId}` - Submit review
- `POST /api/v1/ai/conversations` - Start AI tutor session
- `POST /api/v1/ai/conversations/{id}/messages/stream` - Stream AI response
- `POST /api/v1/admin/content/import` - Bulk import content

## Security

- Code execution runs in isolated Docker containers
- No network access for user code
- Resource limits: 1 CPU, 256MB RAM, 30s timeout
- Read-only filesystem, non-root user
- No `eval()` or direct process execution in application

## Troubleshooting

### Backend won't start
- Verify PostgreSQL is healthy: `docker compose logs postgres`
- Check Flyway migration status: `docker compose logs backend | grep Flyway`
- Ensure database is empty or matches migration state (ddl-auto: validate)

### Frontend shows blank page
- Check browser console for errors
- Verify VITE_API_URL in .env matches backend URL
- Ensure backend is accessible at http://localhost:8080/api/v1

### AI features not working
- Verify Ollama is running: `curl http://localhost:11434/api/tags`
- Pull required models: `ollama pull llama3.1:8b`
- Check backend logs for AI provider errors

### Code execution fails
- Verify runner images built: `docker images | grep dsa-runner`
- Rebuild runners: `docker compose build runner-typescript runner-javascript runner-python`
- Check Docker socket access (backend needs `/var/run/docker.sock`)

### Database connection issues
- Verify DB_PASSWORD in .env matches docker-compose.yml
- Check postgres container health: `docker compose ps postgres`
- Test connection: `docker exec -it dsa-mastery-postgres psql -U dsa_user -d dsa_mastery`

## Deployment (Production)

### Docker Compose (Single Server)

```bash
# Production environment file
cp .env.example .env.prod
# Edit .env.prod with secure passwords

# Build and deploy
docker compose -f docker-compose.yml -f docker-compose.prod.yml up -d
```

Create `docker-compose.prod.yml`:
```yaml
services:
  backend:
    environment:
      SPRING_PROFILES_ACTIVE: prod
      DB_PASSWORD: ${DB_PASSWORD}
    deploy:
      resources:
        limits:
          cpus: '2'
          memory: 2G
    restart: unless-stopped

  frontend:
    restart: unless-stopped

  postgres:
    volumes:
      - postgres_data:/var/lib/postgresql/data
    restart: unless-stopped

  ollama:
    volumes:
      - ollama_data:/root/.ollama
    restart: unless-stopped
```

### Kubernetes (Helm)

A Helm chart is planned for production Kubernetes deployments. Key considerations:
- Use external PostgreSQL (Cloud SQL, RDS, etc.)
- Use managed Ollama or cloud AI (OpenAI, Anthropic)
- Configure ingress with TLS
- Set resource limits and requests
- Configure horizontal pod autoscaling

### Environment Variables for Production

| Variable | Description | Required |
|----------|-------------|----------|
| `DB_PASSWORD` | PostgreSQL password | Yes |
| `OPENAI_API_KEY` | OpenAI API key (cloud AI fallback) | No |
| `SPRING_PROFILES_ACTIVE` | Spring profile (prod) | Yes |
| `VITE_API_URL` | Frontend API base URL | Yes |

## Extending the Platform

### Adding a New Problem

1. Create problem directory under `content/problems/`
2. Write `problem.md` with frontmatter
3. Add solution files in `solutions/` (worst/better/best approaches)
4. Define `test-cases.yaml`
5. Create templates in `templates/`
6. Add `references.yaml`
7. Add to curriculum in `content/curriculum.yaml`
8. Run import pipeline (when implemented) or create Flyway migration

### Adding a New Pattern

1. Create pattern directory under `content/patterns/`
2. Write `pattern.md` with full pedagogical structure
3. Add template files in `templates/`
4. Add `references.yaml`
5. Link problems in curriculum

## License

MIT License - See LICENSE file for details.