# JavaScript/Node.js Runner for DSA Code Execution
FROM node:20-alpine

# Install test framework
RUN npm install -g vitest@2.0.5

# Create non-root user
RUN adduser -D -u 1000 runner

USER runner

WORKDIR /workspace

ENTRYPOINT ["sh", "-c"]