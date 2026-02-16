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
@RequiredArgsConstructor
@RequestMapping("/api/ai")
public class AiAdvisorController
{
    private final AiConsultantService aiService;

    @GetMapping("/analyze")
    public ResponseEntity<Map<String, String>> getInsight(@RequestParam String query) {
        String aiResponse = aiService.generateBusinessInsight(query);
        return ResponseEntity.ok(Map.of("answer", aiResponse));
    }
}
