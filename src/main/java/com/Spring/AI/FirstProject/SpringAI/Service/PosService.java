package com.Spring.AI.FirstProject.SpringAI.Service;

import com.Spring.AI.FirstProject.SpringAI.Entity.ItemEntity;
import com.Spring.AI.FirstProject.SpringAI.Repository.ItemEntityRepository;
import com.Spring.AI.FirstProject.SpringAI.Repository.OrderEntityRepository;
import com.Spring.AI.FirstProject.SpringAI.Repository.OrderItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PosService {

    private final OrderEntityRepository orderRepo;
    private final ItemEntityRepository  itemRepo;
    private final OrderItemRepository orderItemRepo;

    public PosService(OrderEntityRepository orderRepo,
                      ItemEntityRepository itemRepo,
                      OrderItemRepository orderItemRepo) {
        this.orderRepo     = orderRepo;
        this.itemRepo      = itemRepo;
        this.orderItemRepo = orderItemRepo;
    }

    // Daily summary
    public SalesSummary getDailySummary(String date) {
        Double total = orderRepo.getSalesByDate(date);
        Long   count = orderRepo.countByDate(date);
        return new SalesSummary(
                total != null ? total            : 0.0,
                count != null ? count.intValue() : 0
        );
    }

    //  Today's revenue
    public double getTodayRevenue() {
        Double revenue = orderRepo.getTodaySales();
        return revenue != null ? revenue : 0.0;
    }

    // Monthly revenue
    public double getMonthlyRevenue() {
        Double revenue = orderRepo.getMonthlyRevenue();
        return revenue != null ? revenue : 0.0;
    }

    // Top selling products - NOW WORKS WITH REAL DATA
    public String getTopSellingProducts() {
        List<Object[]> results = orderItemRepo.findTopSellingProducts();
        if (results.isEmpty()) {
            return "No sales data available yet.";
        }
        return results.stream()
                .map(row -> String.format(
                        "Product: %-20s | Sold: %-5s units | Revenue: $%s",
                        row[0], row[1], row[2]
                ))
                .collect(Collectors.joining("\n"));
    }

    // Revenue per product
    public String getRevenuePerProduct() {
        List<Object[]> results = orderItemRepo.findRevenuePerProduct();
        if (results.isEmpty()) {
            return "No revenue data available.";
        }
        return results.stream()
                .map(row -> String.format(
                        "%-20s | Units: %-5s | Revenue: $%s",
                        row[0], row[1], row[2]
                ))
                .collect(Collectors.joining("\n"));
    }

    //  All items
    public List<ItemEntity> getAllItems() {
        return itemRepo.findAll();
    }

    //  Items by category
    public String getItemsByCategory(String categoryName) {
        List<ItemEntity> items = itemRepo.findByCategoryName(categoryName);
        if (items.isEmpty()) {
            return "No items found in category: " + categoryName;
        }
        return items.stream()
                .map(i -> String.format(
                        "%s | Price: $%s",
                        i.getName(), i.getPrice()
                ))
                .collect(Collectors.joining("\n"));
    }

    //  Inner class
    public static class SalesSummary {
        private final double amount;
        private final int    count;

        public SalesSummary(double amount, int count) {
            this.amount = amount;
            this.count  = count;
        }

        public double getAmount() { return amount; }
        public int    getCount()  { return count;  }

        @Override
        public String toString() {
            return String.format("Revenue: $%.2f | Orders: %d", amount, count);
        }
    }
}