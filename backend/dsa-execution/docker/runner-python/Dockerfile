# Python Runner for DSA Code Execution
FROM python:3.13-alpine

# Install test framework and timeout plugin
RUN pip install --no-cache-dir pytest==8.1.1 pytest-timeout==2.3.1 pytest-json-report==1.5.0

# Create non-root user (use UID 10000 to avoid conflict)
RUN adduser -D -u 10000 runner

USER runner

WORKDIR /workspace

ENTRYPOINT ["sh", "-c"]