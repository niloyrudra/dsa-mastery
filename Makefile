# DSA Mastery Platform - Makefile
# Common development and deployment commands

.PHONY: help dev prod build up down logs clean test lint typecheck db-migrate db-reset

# Default target
help:
	@echo "DSA Mastery Platform - Available Commands"
	@echo ""
	@echo "Development:"
	@echo "  make dev          Start all services in development mode"
	@echo "  make build        Build all Docker images"
	@echo "  make up           Start services (detached)"
	@echo "  make down         Stop and remove services"
	@echo "  make logs         Follow logs for all services"
	@echo "  make logs-backend Follow backend logs"
	@echo "  make logs-frontend Follow frontend logs"
	@echo ""
	@echo "Backend:"
	@echo "  make backend-build    Build backend JAR"
	@echo "  make backend-test     Run backend tests"
	@echo "  make backend-run      Run backend locally (requires DB & Ollama)"
	@echo ""
	@echo "Frontend:"
	@echo "  make frontend-install Install frontend dependencies"
	@echo "  make frontend-dev     Start frontend dev server"
	@echo "  make frontend-build   Build frontend for production"
	@echo "  make frontend-lint    Run frontend linting"
	@echo "  make frontend-typecheck Run TypeScript type checking"
	@echo ""
	@echo "Database:"
	@echo "  make db-migrate   Run Flyway migrations (via backend)"
	@echo "  make db-reset     Reset database (WARNING: destroys data)"
	@echo "  make db-shell     Open PostgreSQL shell"
	@echo ""
	@echo "Deployment:"
	@echo "  make prod         Deploy to production (uses docker-compose.prod.yml)"
	@echo "  make deploy-dev   Deploy to development using deploy.sh"
	@echo "  make deploy-prod  Deploy to production using deploy.sh"
	@echo ""
	@echo "Maintenance:"
	@echo "  make clean        Remove all containers, volumes, and images"
	@echo "  make prune        Remove unused Docker resources"

# Development
dev: up
	@echo "Services started. Frontend: http://localhost:3000, Backend: http://localhost:8080"

build:
	docker compose build --parallel

up:
	docker compose up -d

down:
	docker compose down

logs:
	docker compose logs -f

logs-backend:
	docker compose logs -f backend

logs-frontend:
	docker compose logs -f frontend

# Backend
backend-build:
	cd backend && ./gradlew :dsa-api:bootJar

backend-test:
	cd backend && ./gradlew test

backend-run:
	cd backend && ./gradlew :dsa-api:bootRun

# Frontend
frontend-install:
	cd frontend && npm install

frontend-dev:
	cd frontend && npm run dev

frontend-build:
	cd frontend && npm run build

frontend-lint:
	cd frontend && npm run lint

frontend-typecheck:
	cd frontend && npm run typecheck

# Database
db-migrate:
	docker compose exec backend ./gradlew :dsa-api:flywayMigrate

db-reset:
	@read -p "This will destroy all data. Continue? (y/N) " confirm && [ "$$confirm" = "y" ]
	docker compose down -v
	docker compose up -d postgres
	@echo "Waiting for PostgreSQL..."
	@sleep 5
	docker compose up -d backend

db-shell:
	docker compose exec postgres psql -U dsa_user -d dsa_mastery

# Deployment
prod:
	docker compose -f docker-compose.yml -f docker-compose.prod.yml up -d --build

deploy-dev:
	./deploy.sh dev

deploy-prod:
	./deploy.sh prod

# Maintenance
clean:
	docker compose down -v --rmi all --remove-orphans

prune:
	docker system prune -af --volumes