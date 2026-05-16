package com.Spring.AI.FirstProject.SpringAI.Tools;


import com.Spring.AI.FirstProject.SpringAI.Repository.ItemEntityRepository;
import com.Spring.AI.FirstProject.SpringAI.Repository.OrderEntityRepository;
import com.Spring.AI.FirstProject.SpringAI.Service.PosService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PosDatabaseTools {

    private final PosService            posService;
    private final ItemEntityRepository  itemRepo;
    private final OrderEntityRepository orderRepo;

    public PosDatabaseTools(PosService posService,
                            ItemEntityRepository itemRepo,
                            OrderEntityRepository orderRepo) {
        this.posService = posService;
        this.itemRepo   = itemRepo;
        this.orderRepo  = orderRepo;
    }

    //  Tool 1
    @Tool(description = "Get total sales amount and order count for a specific date. Format: YYYY-MM-DD")
    public String fetchDailySales(String date) {
        var data = posService.getDailySummary(date);
        return String.format(
                "Date: %s | Total Revenue: $%.2f | Total Orders: %d",
                date, data.getAmount(), data.getCount()
        );
    }

    //  Tool 2
    @Tool(description = "Get today's total revenue")
    public String fetchTodaySales() {
        double revenue = posService.getTodayRevenue();
        return String.format("Today's Revenue: $%.2f", revenue);
    }

    //  Tool 3
    @Tool(description = "Get this month's total revenue and order count")
    public String fetchMonthlySales() {
        Double monthly  = orderRepo.getMonthlyRevenue();
        Long   count    = orderRepo.getTotalOrdersThisMonth();
        Double avg      = orderRepo.getAverageOrderValueToday();
        return String.format(
                "Monthly Revenue: $%.2f | Total Orders: %d | Avg Order: $%.2f",
                monthly != null ? monthly : 0.0,
                count   != null ? count   : 0L,
                avg     != null ? avg     : 0.0
        );
    }

    // Tool 4 - NOW WORKS WITH REAL DATA!
    @Tool(description = "Get top 5 best selling products with units sold and revenue generated")
    public String getTopSellingProducts() {
        return posService.getTopSellingProducts();
    }

    // Tool 5 - NOW WORKS WITH REAL DATA!
    @Tool(description = "Get revenue breakdown per product to identify high and low performing items")
    public String getRevenuePerProduct() {
        return posService.getRevenuePerProduct();
    }

    //  Tool 6
    @Tool(description = "Get full product catalog with prices and categories")
    public String getAllProductDetails() {
        return itemRepo.findAll()
                .stream()
                .limit(50)
                .map(i -> String.format(
                        "%-20s | Price: $%-8s | Category: %s",
                        i.getName(),
                        i.getPrice(),
                        i.getCategory() != null ? i.getCategory().getName() : "N/A"
                ))
                .collect(Collectors.joining("\n"));
    }

    // Tool 7
    @Tool(description = "Get items by category name to analyze category performance")
    public String getItemsByCategory(String categoryName) {
        return posService.getItemsByCategory(categoryName);
    }

    // Tool 8
    @Tool(description = "Get count of items in each category for inventory analysis")
    public String getCategoryBreakdown() {
        return itemRepo.countItemsPerCategory()
                .stream()
                .map(row -> String.format(
                        "Category: %-15s | Items: %s",
                        row[0], row[1]
                ))
                .collect(Collectors.joining("\n"));
    }

    // Tool 9
    @Tool(description = "Get premium products priced above 100 for upsell opportunities")
    public String getPremiumProducts() {
        return itemRepo.findByPriceGreaterThan(new BigDecimal("100"))
                .stream()
                .map(i -> String.format(
                        "%s | $%s",
                        i.getName(), i.getPrice()
                ))
                .collect(Collectors.joining(", "));
    }
}