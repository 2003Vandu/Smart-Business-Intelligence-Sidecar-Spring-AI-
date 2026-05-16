package com.Spring.AI.FirstProject.SpringAI.Controller;

import com.Spring.AI.FirstProject.SpringAI.Service.GeminiAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiController {

    private final GeminiAiService aiService;

    @GetMapping("/chat")
    public String chat(
            @RequestParam(defaultValue = "Hello! What can you help me with?")
            String message) {
        return aiService.generateAnswer(message);
    }
}