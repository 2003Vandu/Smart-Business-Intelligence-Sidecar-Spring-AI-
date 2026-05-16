package com.Spring.AI.FirstProject.SpringAI.Controller;


import com.Spring.AI.FirstProject.SpringAI.Service.AiConsultantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiAdvisorController {

    private final AiConsultantService aiService;

    @GetMapping("/analyze")
    public ResponseEntity<Map<String, String>> getInsight(
            @RequestParam String query) {

        if (query == null || query.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Query cannot be empty"));
        }

        String response = aiService.generateBusinessInsight(query);
        return ResponseEntity.ok(Map.of("answer", response));
    }
}