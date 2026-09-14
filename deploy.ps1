<# 
.SYNOPSIS
    Deploy script for DSA Mastery Platform (PowerShell version for Windows)

.DESCRIPTION
    Deploys the DSA Mastery platform using Docker Compose.
    Supports both development and production environments.

.PARAMETER Environment
    Target environment: 'dev' (default) or 'prod'

.EXAMPLE
    .\deploy.ps1 dev
    .\deploy.ps1 prod
#>

param(
    [ValidateSet('dev', 'prod')]
    [string]$Environment = 'dev'
)

$ErrorActionPreference = 'Stop'

$composeFiles = @('-f', 'docker-compose.yml')
$envLabel = 'DEVELOPMENT'

if ($Environment -eq 'prod') {
    $composeFiles += '-f', 'docker-compose.prod.yml'
    $envLabel = 'PRODUCTION'
}

Write-Host "🚀 Deploying to $envLabel" -ForegroundColor Green

# Check for .env file
if (-not (Test-Path '.env')) {
    if (Test-Path '.env.example') {
        Write-Host "📋 Creating .env from .env.example" -ForegroundColor Yellow
        Copy-Item '.env.example' '.env'
    } else {
        Write-Host "❌ No .env.example found" -ForegroundColor Red
        exit 1
    }
}

# Load environment variables
$envContent = Get-Content '.env' -Raw
$envContent -split "`n" | ForEach-Object {
    if ($_ -match '^([^#=]+)=(.*)$') {
        $name = $matches[1].Trim()
        $value = $matches[2].Trim()
        if ($name -and $value) {
            [Environment]::SetEnvironmentVariable($name, $value, 'Process')
        }
    }
}

# Validate required variables
if (-not $env:DB_PASSWORD) {
    Write-Host "❌ DB_PASSWORD not set in .env" -ForegroundColor Red
    exit 1
}

Write-Host "🔨 Building images..." -ForegroundColor Cyan
docker compose @composeFiles build --parallel

Write-Host "🚀 Starting services..." -ForegroundColor Cyan
docker compose @composeFiles up -d

Write-Host "⏳ Waiting for services to be healthy..." -ForegroundColor Yellow
Start-Sleep -Seconds 10

function Check-Service {
    param(
        [string]$Name,
        [string]$Url,
        [int]$MaxAttempts = 30
    )
    
    Write-Host "  Checking $Name..." -NoNewline
    for ($i = 1; $i -le $MaxAttempts; $i++) {
        try {
            $response = Invoke-WebRequest -Uri $Url -Method Head -TimeoutSec 5 -ErrorAction Stop
            if ($response.StatusCode -eq 200) {
                Write-Host " ✅" -ForegroundColor Green
                return $true
            }
        } catch {
            # Ignore errors, just retry
        }
        Start-Sleep -Seconds 2
    }
    Write-Host " ❌" -ForegroundColor Red
    return $false
}

# Check PostgreSQL
try {
    $result = docker compose @composeFiles exec -T postgres pg_isready -U dsa_user -d dsa_mastery
    if ($LASTEXITCODE -eq 0) {
        Write-Host "  ✅ PostgreSQL is healthy" -ForegroundColor Green
    } else {
        Write-Host "  ❌ PostgreSQL health check failed" -ForegroundColor Red
    }
} catch {
    Write-Host "  ❌ PostgreSQL health check failed" -ForegroundColor Red
}

# Check services
Check-Service "Backend" "http://localhost:8080/actuator/health"
Check-Service "Ollama" "http://localhost:11434/api/tags"
Check-Service "Frontend" "http://localhost:3000"

Write-Host ""
Write-Host "✅ Deployment complete!" -ForegroundColor Green
Write-Host ""
Write-Host "📍 Access points:" -ForegroundColor Cyan
Write-Host "   Frontend:  http://localhost:3000"
Write-Host "   Backend:   http://localhost:8080/api/v1"
Write-Host "   Swagger:   http://localhost:8080/docs"
Write-Host "   Ollama:    http://localhost:11434"
Write-Host ""
Write-Host "📋 Useful commands:" -ForegroundColor Cyan
Write-Host "   View logs:      docker compose $($composeFiles -join ' ') logs -f"
Write-Host "   Stop services:  docker compose $($composeFiles -join ' ') down"
Write-Host "   Restart:        docker compose $($composeFiles -join ' ') restart"