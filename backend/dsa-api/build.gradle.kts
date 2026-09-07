plugins {
    id("java")
    id("kotlin")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    implementation(project(":dsa-common"))
    implementation(project(":dsa-content"))
    implementation(project(":dsa-curriculum"))
    implementation(project(":dsa-problems"))
    implementation(project(":dsa-patterns"))
    implementation(project(":dsa-templates"))
    implementation(project(":dsa-mastery"))
    implementation(project(":dsa-execution"))
    implementation(project(":dsa-ai"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // MapStruct DTO mapping (mappers live in this module)
    implementation("org.mapstruct:mapstruct:1.5.5.Final")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
    // Lombok must run before MapStruct so builders exist; binding links the two
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")
}



repositories {
    mavenCentral()
    // Spring AI artifacts from :dsa-ai (published to the milestone repo, not Maven Central)
    maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.11.3"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-junit-jupiter")
    testImplementation("org.assertj:assertj-core:3.26.3")
    testImplementation("org.testcontainers:junit-jupiter:1.20.3")
    testImplementation("org.testcontainers:postgresql:1.20.3")
    testImplementation("org.springframework.boot:spring-boot-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf(
            "-Xjsr305=strict",
            "-Xopt-in=kotlin.RequiresOptIn",
            "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
        )
        jvmTarget = "21"
    }
}
