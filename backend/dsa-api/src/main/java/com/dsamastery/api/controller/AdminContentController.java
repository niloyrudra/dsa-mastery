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

@RestController
@RequestMapping("/api/v1/admin/content")
@RequiredArgsConstructor
public class AdminContentController {

    private final ContentImportService contentImportService;

    @PostMapping("/import")
    public ResponseEntity<ContentImportResponse> importContent(
            @RequestBody(required = false) Map<String, Object> request) {

        String root = "./content";
        if (request != null && request.containsKey("contentRoot")) {
            Object contentRoot = request.get("contentRoot");
            if (contentRoot != null && !contentRoot.toString().isBlank()) {
                root = contentRoot.toString();
            }
        }

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

    public record ContentImportResponse(
        int imported,
        List<String> errors,
        Map<String, Integer> counts,
        boolean hasErrors
    ) {}
}