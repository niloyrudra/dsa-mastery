<# 
.SYNOPSIS
    Pulls required Ollama models for DSA Mastery Platform AI Tutor

.DESCRIPTION
    Pulls three models configured in the AI architecture:
    - llama3.1:8b (FAST) - Classification, tagging, simple explanation
    - codellama:13b (CODING) - Code review, algorithm reasoning, template mapping
    - deepseek-coder:33b (REASONING) - Content generation, difficult explanations, variation generation
#>

param(
    [string]$OllamaHost = "http://localhost:11434"
)

$models = @(
    @{ Name = "llama3.1:8b"; Role = "FAST"; Size = "4.7GB" },
    @{ Name = "codellama:13b"; Role = "CODING"; Size = "7.3GB" },
    @{ Name = "deepseek-coder:33b"; Role = "REASONING"; Size = "19GB" }
)

Write-Host "==========================================" -ForegroundColor Cyan
Write-Host "DSA Mastery - Ollama Model Setup" -ForegroundColor Cyan
Write-Host "==========================================" -ForegroundColor Cyan
Write-Host "Ollama Host: $OllamaHost" -ForegroundColor Gray
Write-Host ""

# Wait for Ollama to be ready
Write-Host "Waiting for Ollama to be ready..." -ForegroundColor Yellow
for ($i = 1; $i -le 30; $i++) {
    try {
        $response = Invoke-RestMethod -Uri "$OllamaHost/api/tags" -Method Get -ErrorAction Stop
        Write-Host "Ollama is ready!" -ForegroundColor Green
        break
    } catch {
        Write-Host "  Waiting... ($i/30)" -ForegroundColor Gray
        Start-Sleep -Seconds 2
    }
}

# Check if Ollama is actually ready
try {
    Invoke-RestMethod -Uri "$OllamaHost/api/tags" -Method Get -ErrorAction Stop | Out-Null
} catch {
    Write-Host "ERROR: Ollama is not responding at $OllamaHost" -ForegroundColor Red
    exit 1
}

# Pull each model
foreach ($model in $models) {
    Write-Host "" 
    Write-Host "----------------------------------------" -ForegroundColor Cyan
    Write-Host "Checking model: $($model.Name) ($($model.Role) - $($model.Size))" -ForegroundColor Cyan
    Write-Host "----------------------------------------" -ForegroundColor Cyan
    
    # Check if model already exists
    $existingModels = Invoke-RestMethod -Uri "$OllamaHost/api/tags" -Method Get
    $exists = $existingModels.models | Where-Object { $_.name -eq $model.Name }
    
    if ($exists) {
        Write-Host "Model $($model.Name) already exists, skipping pull." -ForegroundColor Green
    } else {
        Write-Host "Pulling $($model.Name)..." -ForegroundColor Yellow
        
        try {
            $body = @{ name = $model.Name } | ConvertTo-Json
            $response = Invoke-RestMethod -Uri "$OllamaHost/api/pull" -Method Post -Body $body -ContentType "application/json" -ErrorAction Stop
            
            # The pull API streams responses, but Invoke-RestMethod waits for completion
            Write-Host "Successfully pulled $($model.Name)" -ForegroundColor Green
        } catch {
            Write-Host "WARNING: Failed to pull $($model.Name) (may already exist or network issue)" -ForegroundColor Yellow
            Write-Host "  Error: $($_.Exception.Message)" -ForegroundColor Gray
        }
    }
}

Write-Host ""
Write-Host "==========================================" -ForegroundColor Cyan
Write-Host "Model setup complete!" -ForegroundColor Cyan
Write-Host "==========================================" -ForegroundColor Cyan
Write-Host ""

Write-Host "Available models:" -ForegroundColor Gray
try {
    $models = Invoke-RestMethod -Uri "$OllamaHost/api/tags" -Method Get
    $models.models | ForEach-Object {
        $sizeGB = [math]::Round($_.size / 1GB, 1)
        Write-Host "  $($_.name) (${sizeGB}GB)" -ForegroundColor Gray
    }
} catch {
    Write-Host "  Could not list models" -ForegroundColor Red
}