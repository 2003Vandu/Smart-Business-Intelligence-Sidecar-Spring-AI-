package com.Spring.AI.FirstProject.SpringAI.Controller;

import com.Spring.AI.FirstProject.SpringAI.Records.BusinessInsight;
import com.Spring.AI.FirstProject.SpringAI.Service.AiOrchestratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiSidecarController {

    private final AiOrchestratorService aiService;

    @GetMapping("/insight")
    public BusinessInsight analyze(@RequestParam String query) {
        return aiService.generateAnalysis(query);
    }
}