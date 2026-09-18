package com.dsamastery.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/echo")
    public Map<String, Object> echo(@RequestBody String body) {
        try {
            Map<String, Object> parsed = objectMapper.readValue(body, Map.class);
            return Map.of("received", parsed, "class", parsed.getClass().getName(), "rawBody", body);
        } catch (Exception e) {
            return Map.of("error", e.getMessage(), "rawBody", body);
        }
    }
}