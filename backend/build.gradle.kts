plugins {
    id("org.springframework.boot") version "3.3.3" apply false
    id("io.spring.dependency-management") version "1.1.6" apply false
    id("org.jetbrains.kotlin.jvm") version "2.0.20" apply false
    id("org.jetbrains.kotlin.plugin.spring") version "2.0.20" apply false
    id("org.jetbrains.kotlin.plugin.jpa") version "2.0.20" apply false
    id("io.gitlab.arturbosch.detekt") version "1.23.6" apply false
}

// Root project - minimal configuration
group = "com.dsamastery"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

configurations.all {
    resolutionStrategy {
        force("org.flywaydb:flyway-core:11.3.0")
        force("org.flywaydb:flyway-database-postgresql:11.3.0")
    }
}