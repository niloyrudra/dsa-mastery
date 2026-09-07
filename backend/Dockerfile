# Multi-stage build for Spring Boot application
FROM gradle:8.5-jdk21 AS builder

WORKDIR /app

# Copy gradle files
    COPY settings.gradle.kts build.gradle.kts gradle.properties ./

# Copy all module build files
COPY dsa-common/build.gradle.kts dsa-common/
COPY dsa-content/build.gradle.kts dsa-content/
COPY dsa-curriculum/build.gradle.kts dsa-curriculum/
COPY dsa-problems/build.gradle.kts dsa-problems/
COPY dsa-patterns/build.gradle.kts dsa-patterns/
COPY dsa-templates/build.gradle.kts dsa-templates/
COPY dsa-algorithms/build.gradle.kts dsa-algorithms/
COPY dsa-data-structures/build.gradle.kts dsa-data-structures/
COPY dsa-mastery/build.gradle.kts dsa-mastery/
COPY dsa-execution/build.gradle.kts dsa-execution/
COPY dsa-ai/build.gradle.kts dsa-ai/
COPY dsa-api/build.gradle.kts dsa-api/

# Download dependencies
RUN gradle dependencies --no-daemon

# Copy source code
COPY . .

# Build the application
RUN gradle :dsa-api:bootJar --no-daemon -x test

# Runtime stage
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Install curl for health checks
RUN apk add --no-cache curl

# Create non-root user
RUN addgroup -g 1000 -S appgroup && \
    adduser -u 1000 -S appuser -G appgroup

# Copy built JAR
COPY --from=builder /app/dsa-api/build/libs/*.jar app.jar

# Change ownership
RUN chown appuser:appgroup app.jar

USER appuser

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
    CMD curl -f http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "/app/app.jar"]