package com.Spring.AI.FirstProject.SpringAI.Service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class GeminiAiService {

    private final ChatClient chatClient;

    public GeminiAiService(ChatClient.Builder builder) {
        this.chatClient = builder
                .defaultSystem("""
                You are a helpful POS system assistant.
                Answer clearly and concisely in 2-3 sentences.
                """)
                .build();
    }

    public String generateAnswer(String userPrompt) {
        try {
            return chatClient.prompt()
                    .user(userPrompt)
                    .call()
                    .content();
        } catch (Exception e) {
            return "AI service unavailable. Please try again.";
        }
    }
}

