package com.Spring.AI.FirstProject.SpringAI.Repository;

import com.Spring.AI.FirstProject.SpringAI.Entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long>
{

    // Top selling products using real tbl_orderItems table
    @Query(value = """
            SELECT oi.name, 
                   SUM(oi.quantity) as total_sold,
                   SUM(oi.price * oi.quantity) as total_revenue
            FROM tbl_orderItems oi
            GROUP BY oi.name
            ORDER BY total_sold DESC
            LIMIT 5
            """, nativeQuery = true)
    List<Object[]> findTopSellingProducts();

    // Revenue per product
    @Query(value = """
            SELECT oi.name,
                   SUM(oi.quantity) as units_sold,
                   SUM(oi.price * oi.quantity) as revenue
            FROM tbl_orderItems oi
            GROUP BY oi.name
            ORDER BY revenue DESC
            """, nativeQuery = true)
    List<Object[]> findRevenuePerProduct();
}
