package com.Spring.AI.FirstProject.SpringAI.Service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class GeminiAiService {

    private final ChatClient chatClient;

    public GeminiAiService(ChatClient.Builder builder) {
        // We build the client with a default "System" prompt
        this.chatClient = builder
                .defaultSystem("You are a helpful assistant that answers in exactly 2 sentences.")
                .build();
    }

    public String generateAnswer(String userPrompt) {
        return chatClient.prompt()
                .user(userPrompt)
                .call()
                .content();
    }
}