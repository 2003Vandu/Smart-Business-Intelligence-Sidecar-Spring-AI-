package com.Spring.AI.FirstProject.SpringAI.Service;

import com.Spring.AI.FirstProject.SpringAI.Entity.ItemEntity;
import com.Spring.AI.FirstProject.SpringAI.Entity.OrderEntity;
import com.Spring.AI.FirstProject.SpringAI.Repository.ItemEntityRepository;
import com.Spring.AI.FirstProject.SpringAI.Repository.OrderEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AiConsultantService {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(AiConsultantService.class);
    private final ChatClient chatClient;
    private final OrderEntityRepository orderRepo; // A simple JpaRepository
    private final ItemEntityRepository itemEntityRepository;


    // Constructor Dependency injection
    public AiConsultantService(ChatClient.Builder builder, OrderEntityRepository orderRepo, ItemEntityRepository itemEntityRepository) {
        this.chatClient = builder.build();
        this.orderRepo = orderRepo;
        this.itemEntityRepository = itemEntityRepository;
    }

    public String generateBusinessInsight(String userQuery) {
        // 1. RETRIEVE data from your POS Database
        List<OrderEntity> recentOrders = orderRepo.findTop10ByOrderByCreatedAtDesc();
        Double todayRevenue = orderRepo.getTodaySales();


        List<ItemEntity> items =itemEntityRepository.findAll();

        // 3. Build Multi-Data Context
        String context = String.format(
                "REVENUE DATA: Today: %s. Recent Orders: %s\n" +
                        "INVENTORY DATA: Available Products: %s",
                (todayRevenue != null ? todayRevenue : 0.0),
                recentOrders.toString(),
                items.toString()
        );

        logger.info("Sending following context to AI: \n{}" + context);

        // 3. GENERATE response via Spring AI (gemini)
        return chatClient.prompt()
                .system("You are a Business Intelligence Bot for a POS system. " +

                        "Analyze both Sales history and Product lists to provide strategies.")

                .user("Context:\n" + context + "\n\nUser Question: " + userQuery)
                .call()
                .content();
    }
}
