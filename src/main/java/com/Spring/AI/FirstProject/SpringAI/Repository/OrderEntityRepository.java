package com.Spring.AI.FirstProject.SpringAI.Repository;


import com.Spring.AI.FirstProject.SpringAI.Entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderEntityRepository extends JpaRepository<OrderEntity,Long>
{
    //  Recent 10 orders
    List<OrderEntity> findTop10ByOrderByCreatedAtDesc();

    // Today's total sales
    @Query("SELECT SUM(o.grandTotal) FROM OrderEntity o " +
            "WHERE o.createdAt >= CURRENT_DATE")
    Double getTodaySales();

    // Sales by specific date
    @Query("SELECT SUM(o.grandTotal) FROM OrderEntity o " +
            "WHERE CAST(o.createdAt AS date) = CAST(:date AS date)")
    Double getSalesByDate(@Param("date") String date);

    //  Count orders by date
    @Query("SELECT COUNT(o) FROM OrderEntity o " +
            "WHERE CAST(o.createdAt AS date) = CAST(:date AS date)")
    Long countByDate(@Param("date") String date);

    //  Monthly revenue
    @Query("SELECT SUM(o.grandTotal) FROM OrderEntity o " +
            "WHERE MONTH(o.createdAt) = MONTH(CURRENT_DATE) " +
            "AND YEAR(o.createdAt) = YEAR(CURRENT_DATE)")
    Double getMonthlyRevenue();

    //  Monthly order count
    @Query("SELECT COUNT(o) FROM OrderEntity o " +
            "WHERE MONTH(o.createdAt) = MONTH(CURRENT_DATE) " +
            "AND YEAR(o.createdAt) = YEAR(CURRENT_DATE)")
    Long getTotalOrdersThisMonth();

    //  Average order value today
    @Query("SELECT AVG(o.grandTotal) FROM OrderEntity o " +
            "WHERE o.createdAt >= CURRENT_DATE")
    Double getAverageOrderValueToday();

    // Orders after certain time
    List<OrderEntity> findByCreatedAtAfter(LocalDateTime dateTime);

}
