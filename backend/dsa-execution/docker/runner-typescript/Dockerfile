# TypeScript/Node.js Runner for DSA Code Execution
FROM node:20-alpine

# Install TypeScript tooling and test framework
RUN npm install -g typescript@5.3.3 ts-node@10.9.2 vitest@2.0.5 @types/node@20.10.0

# Create non-root user (use UID 10000 to avoid conflict)
RUN adduser -D -u 10000 runner

USER runner

WORKDIR /workspace

ENTRYPOINT ["sh"]