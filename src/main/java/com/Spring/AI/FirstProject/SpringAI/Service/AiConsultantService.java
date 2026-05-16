package com.Spring.AI.FirstProject.SpringAI.Service;

import com.Spring.AI.FirstProject.SpringAI.Entity.ItemEntity;
import com.Spring.AI.FirstProject.SpringAI.Entity.OrderEntity;
import com.Spring.AI.FirstProject.SpringAI.Repository.ItemEntityRepository;
import com.Spring.AI.FirstProject.SpringAI.Repository.OrderEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class AiConsultantService {

    private static final Logger logger = LoggerFactory.getLogger(AiConsultantService.class);

    private final ChatClient            chatClient;
    private final OrderEntityRepository orderRepo;
    private final ItemEntityRepository  itemRepo;
    private final PosService            posService;

    public AiConsultantService(ChatClient.Builder builder,
                               OrderEntityRepository orderRepo,
                               ItemEntityRepository itemRepo,
                               PosService posService) {
        this.chatClient = builder.build();
        this.orderRepo  = orderRepo;
        this.itemRepo   = itemRepo;
        this.posService = posService;
    }

    public String generateBusinessInsight(String userQuery) {
        try {
            // 1. Fetch data
            List<OrderEntity> recentOrders = orderRepo.findTop10ByOrderByCreatedAtDesc();
            double todayRevenue            = posService.getTodayRevenue();
            double monthlyRevenue          = posService.getMonthlyRevenue();
            String topProducts             = posService.getTopSellingProducts();

            // 2. Build order summary (NO customer names for privacy)
            String orderSummary = recentOrders.stream()
                    .map(o -> String.format(
                            "Order: %s | Total: $%.2f",
                            o.getOrderId(),
                            o.getGrandTotal()
                    ))
                    .collect(Collectors.joining("\n"));

            // 3. Build items summary
            String itemsSummary = itemRepo
                    .findTopItems(PageRequest.of(0,10))
                    .stream()
                    .map(i -> String.format(
                            "%s | $%s | Category: %s",
                            i.getName(),
                            i.getPrice(),
                            i.getCategory() != null ? i.getCategory().getName() : "N/A"
                    ))
                    .collect(Collectors.joining("\n"));

            // 4. Build context
            String context = String.format("""
                === REVENUE SUMMARY ===
                Today's Revenue  : $%.2f
                Monthly Revenue  : $%.2f
                
                === TOP SELLING PRODUCTS ===
                %s
                
                === RECENT ORDERS (Last 10) ===
                %s
                
                === PRODUCT CATALOG (Sample) ===
                %s
                """,
                    todayRevenue,
                    monthlyRevenue,
                    topProducts,
                    orderSummary,
                    itemsSummary
            );

            logger.info("Sending context to AI | Query: {}", userQuery);

            // 5. Call AI
            return chatClient.prompt()
                    .system("""
                    You are a Business Intelligence Analyst for a POS system.
                    Analyze ONLY the provided data.
                    Never hallucinate numbers.
                    Give actionable recommendations.
                    Keep response under 200 words.
                    """)
                    .user("Data:\n" + context + "\n\nQuestion: " + userQuery)
                    .call()
                    .content();

        } catch (Exception e) {
            logger.error("Error generating business insight", e);
            return "Unable to generate insight at this time. Please try again.";
        }
    }
}
