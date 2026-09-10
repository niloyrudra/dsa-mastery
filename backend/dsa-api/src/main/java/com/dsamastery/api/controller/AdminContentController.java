package com.dsamastery.api.controller;

import com.dsamastery.content.service.ContentImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * Admin endpoint to run the content import pipeline (validate & import).
 * POST /api/v1/admin/content/import  {"contentRoot": "path/to/content"}
 */
@RestController
@RequestMapping("/api/v1/admin/content")
@RequiredArgsConstructor
public class AdminContentController {

    private final ContentImportService contentImportService;

    @PostMapping("/import")
    public ResponseEntity<ContentImportResponse> importContent(
            @RequestBody(required = false) ContentImportRequest request) {

        String root = (request != null && request.contentRoot() != null && !request.contentRoot().isBlank())
            ? request.contentRoot()
            : "./content";

        try {
            ContentImportService.ImportResult result = contentImportService.importContent(Path.of(root));
            return ResponseEntity.ok(new ContentImportResponse(
                result.getImported(),
                result.getErrors(),
                result.getCounts(),
                !result.getErrors().isEmpty()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ContentImportResponse(
                0,
                List.of(e.getMessage()),
                Map.of(),
                true));
        }
    }

    public record ContentImportRequest(String contentRoot) {}

    public record ContentImportResponse(
        int imported,
        List<String> errors,
        Map<String, Integer> counts,
        boolean hasErrors
    ) {}
}