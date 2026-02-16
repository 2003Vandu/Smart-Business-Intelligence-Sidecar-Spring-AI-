package com.Spring.AI.FirstProject.SpringAI.Controller;

import com.Spring.AI.FirstProject.SpringAI.Service.GeminiAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    @Autowired
    private GeminiAiService aiService;

    @GetMapping("/chat")
    public String chat(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return aiService.generateAnswer(message);
    }
}