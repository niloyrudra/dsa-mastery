package com.dsamastery.api.config;

import com.dsamastery.content.service.ContentImportService;
import com.dsamastery.curriculum.service.CurriculumImportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(1)
@Profile("!test")
public class DataInitializer implements ApplicationRunner {

    private final ContentImportService contentImportService;
    private final CurriculumImportService curriculumImportService;

    @Override
    public void run(ApplicationArguments args) {
        String contentRoot = "./Content";
        
        // Check if content root is provided as argument
        if (args.containsOption("content-root")) {
            contentRoot = args.getOptionValues("content-root").get(0);
        }

        // Try to resolve from current working directory
        Path contentPath = Paths.get(contentRoot).toAbsolutePath();
        
        if (!contentPath.toFile().exists()) {
            // Try from project root (parent of backend/dsa-api directory)
            Path projectRoot = Paths.get(".").toAbsolutePath().normalize();
            // If we're in backend/dsa-api or backend directory, go up to project root
            String lastSegment = projectRoot.getFileName().toString();
            if ("dsa-api".equals(lastSegment) || "backend".equals(lastSegment)) {
                projectRoot = projectRoot.getParent();
                if ("backend".equals(projectRoot.getFileName().toString())) {
                    projectRoot = projectRoot.getParent();
                }
            }
            contentPath = projectRoot.resolve("Content").normalize();
            log.info("Trying content directory at: {}", contentPath);
        }
        
        if (!contentPath.toFile().exists()) {
            log.warn("Content directory not found at: {}. Skipping content import.", contentPath);
            return;
        }

        log.info("Starting content import from: {}", contentPath);
        
        try {
            var result = contentImportService.importContent(contentPath);
            log.info("Content import completed: {}", result);
            
            if (result.getErrors().isEmpty()) {
                log.info("Successfully imported {} items", result.getImported());
            } else {
                log.warn("Content import completed with {} errors:", result.getErrors().size());
                result.getErrors().forEach(e -> log.warn("  - {}", e));
            }
        } catch (Exception e) {
            log.error("Failed to import content: {}", e.getMessage(), e);
        }

        // Import curriculum
        Path curriculumFile = contentPath.resolve("curriculum.yaml");
        if (Files.exists(curriculumFile)) {
            log.info("Starting curriculum import from: {}", curriculumFile);
            try {
                var curriculumResult = curriculumImportService.importCurriculum(curriculumFile);
                log.info("Curriculum import completed: {}", curriculumResult);
                
                if (curriculumResult.getErrors().isEmpty()) {
                    log.info("Successfully imported {} curriculum items", curriculumResult.getImported());
                } else {
                    log.warn("Curriculum import completed with {} errors:", curriculumResult.getErrors().size());
                    curriculumResult.getErrors().forEach(e -> log.warn("  - {}", e));
                }
            } catch (Exception e) {
                log.error("Failed to import curriculum: {}", e.getMessage(), e);
            }
        } else {
            log.warn("Curriculum file not found at: {}", curriculumFile);
        }
    }
}