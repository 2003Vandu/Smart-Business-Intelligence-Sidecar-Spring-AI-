package com.Spring.AI.FirstProject.SpringAI.Repository;


import com.Spring.AI.FirstProject.SpringAI.Entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderEntityRepository extends JpaRepository<OrderEntity,Long>
{
      List<OrderEntity> findTop10ByOrderByCreatedAtDesc();

    // Custom JPQL: To give the AI the total sales for today
    @Query("SELECT SUM(o.grandTotal) FROM OrderEntity o WHERE o.createdAt >= CURRENT_DATE")
    Double getTodaySales();
}
