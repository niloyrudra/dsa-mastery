package com.dsamastery.api.controller;

import com.dsamastery.api.dto.SearchResponse;
import com.dsamastery.api.dto.SearchResultDto;
import com.dsamastery.api.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    public ResponseEntity<SearchResponse> search(
            @RequestParam String q,
            @RequestParam(required = false) String type,
            @RequestParam(defaultValue = "10") int limit) {

        List<SearchResultDto> results = searchService.search(q, type, limit);
        return ResponseEntity.ok(SearchResponse.builder()
            .results(results)
            .build());
    }
}