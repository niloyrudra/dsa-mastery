#!/usr/bin/env bash
# Deploy script for DSA Mastery Platform
# Usage: ./deploy.sh [dev|prod]

set -euo pipefail

ENV="${1:-dev}"
COMPOSE_FILES=("-f" "docker-compose.yml")

if [[ "$ENV" == "prod" ]]; then
    COMPOSE_FILES+=("-f" "docker-compose.prod.yml")
    echo "🚀 Deploying to PRODUCTION"
else
    echo "🛠️  Deploying to DEVELOPMENT"
fi

# Check for .env file
if [[ ! -f ".env" ]]; then
    if [[ -f ".env.example" ]]; then
        echo "📋 Creating .env from .env.example"
        cp .env.example .env
    else
        echo "❌ No .env.example found"
        exit 1
    fi
fi

# Load environment variables
set -a
source .env
set +a

# Validate required variables
if [[ -z "${DB_PASSWORD:-}" ]]; then
    echo "❌ DB_PASSWORD not set in .env"
    exit 1
fi

echo "🔨 Building images..."
docker compose "${COMPOSE_FILES[@]}" build --parallel

echo "🚀 Starting services..."
docker compose "${COMPOSE_FILES[@]}" up -d

echo "⏳ Waiting for services to be healthy..."
sleep 10

# Health checks
check_service() {
    local name=$1
    local url=$2
    local max_attempts=30
    local attempt=1
    
    echo "  Checking $name..."
    while [[ $attempt -le $max_attempts ]]; do
        if curl -sf "$url" >/dev/null 2>&1; then
            echo "  ✅ $name is healthy"
            return 0
        fi
        sleep 2
        ((attempt++))
    done
    echo "  ❌ $name failed health check"
    return 1
}

# Check PostgreSQL
if docker compose "${COMPOSE_FILES[@]}" exec -T postgres pg_isready -U dsa_user -d dsa_mastery >/dev/null 2>&1; then
    echo "  ✅ PostgreSQL is healthy"
else
    echo "  ❌ PostgreSQL health check failed"
fi

# Check backend
check_service "Backend" "http://localhost:8080/actuator/health"

# Check Ollama
check_service "Ollama" "http://localhost:11434/api/tags"

# Check frontend
check_service "Frontend" "http://localhost:3000"

echo ""
echo "✅ Deployment complete!"
echo ""
echo "📍 Access points:"
echo "   Frontend:  http://localhost:3000"
echo "   Backend:   http://localhost:8080/api/v1"
echo "   Swagger:   http://localhost:8080/docs"
echo "   Ollama:    http://localhost:11434"
echo ""
echo "📋 Useful commands:"
echo "   View logs:      docker compose ${COMPOSE_FILES[@]} logs -f"
echo "   Stop services:  docker compose ${COMPOSE_FILES[@]} down"
echo "   Restart:        docker compose ${COMPOSE_FILES[@]} restart"