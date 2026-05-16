package com.Spring.AI.FirstProject.SpringAI.Service;

import com.Spring.AI.FirstProject.SpringAI.Records.BusinessInsight;
import com.Spring.AI.FirstProject.SpringAI.Tools.PosDatabaseTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AiOrchestratorService {

    private final ChatClient chatClient;

    public AiOrchestratorService(ChatClient.Builder builder,
                                 PosDatabaseTools posDatabaseTools) {
        this.chatClient = builder
                .defaultSystem("""
                You are a Senior Business Analyst for a POS (Point of Sale) system.
                
                YOU HAVE ACCESS TO THESE DATA SOURCES:
                1. Daily and Monthly Revenue figures
                2. Order counts and average order values
                3. Top selling products (by quantity and revenue)
                4. Full product catalog with categories and prices
                5. Category breakdown and performance
                
                YOUR STRATEGY:
                - For revenue questions     → use fetchTodaySales or fetchMonthlySales
                - For product trends        → use getTopSellingProducts
                - For inventory questions   → use getAllProductDetails
                - For category analysis     → use getCategoryBreakdown
                - For specific date sales   → use fetchDailySales
                - For premium products      → use getPremiumProducts
                
                YOUR RULES:
                1. Always call the relevant tool before answering
                2. Never guess or make up numbers
                3. Never reveal customer names or phone numbers
                4. Give actionable business recommendations
                5. Be concise and professional
                """)
                .defaultTools(posDatabaseTools)
                .build();
    }

    public BusinessInsight generateAnalysis(String userMessage) {
        return chatClient.prompt()
                .user(userMessage)
                .call()
                .entity(BusinessInsight.class);
    }
}