# JavaScript/Node.js Runner for DSA Code Execution
FROM node:20-alpine

# Install test framework
RUN npm install -g vitest@2.0.5

# Create non-root user (use UID 10000 to avoid conflict)
RUN adduser -D -u 10000 runner

USER runner

WORKDIR /workspace

ENTRYPOINT ["sh"]