#!/bin/bash
# Ollama Model Pull Script for DSA Mastery Platform
# Pulls required models for AI Tutor functionality

set -e

OLLAMA_HOST="${OLLAMA_HOST:-http://localhost:11434}"

# Model configuration per AI Architecture docs
# FAST: Classification, tagging, simple explanation
# CODING: Code review, algorithm reasoning, template mapping
# REASONING: Content generation, difficult explanations, variation generation

MODELS=(
    "llama3.1:8b"      # FAST - 4.7GB
    "codellama:13b"    # CODING - 7.3GB  
    "deepseek-coder:33b" # REASONING - 19GB
)

echo "=========================================="
echo "DSA Mastery - Ollama Model Setup"
echo "=========================================="
echo "Ollama Host: $OLLAMA_HOST"
echo ""

# Wait for Ollama to be ready
echo "Waiting for Ollama to be ready..."
for i in {1..30}; do
    if curl -s "$OLLAMA_HOST/api/tags" > /dev/null 2>&1; then
        echo "Ollama is ready!"
        break
    fi
    echo "  Waiting... ($i/30)"
    sleep 2
done

# Check if Ollama is actually ready
if ! curl -s "$OLLAMA_HOST/api/tags" > /dev/null 2>&1; then
    echo "ERROR: Ollama is not responding at $OLLAMA_HOST"
    exit 1
fi

# Pull each model
for model in "${MODELS[@]}"; do
    echo ""
    echo "----------------------------------------"
    echo "Checking model: $model"
    echo "----------------------------------------"
    
    # Check if model already exists
    if curl -s "$OLLAMA_HOST/api/tags" | grep -q "\"name\":\"$model\""; then
        echo "Model $model already exists, skipping pull."
    else
        echo "Pulling $model..."
        if curl -X POST "$OLLAMA_HOST/api/pull" \
            -H "Content-Type: application/json" \
            -d "{\"name\": \"$model\"}" \
            --no-buffer 2>&1 | while IFS= read -r line; do
            # Parse and display progress
            if echo "$line" | grep -q '"status"'; then
                status=$(echo "$line" | sed -n 's/.*"status":"\([^"]*\)".*/\1/p')
                echo "  $status"
            fi
        done; then
            echo "Successfully pulled $model"
        else
            echo "WARNING: Failed to pull $model (may already exist or network issue)"
        fi
    fi
done

echo ""
echo "=========================================="
echo "Model setup complete!"
echo "=========================================="
echo ""
echo "Available models:"
curl -s "$OLLAMA_HOST/api/tags" | jq -r '.models[] | "  \(.name) (\(.size | tostring | .[0:3])GB)"' 2>/dev/null || \
    curl -s "$OLLAMA_HOST/api/tags"