package com.Spring.AI.FirstProject.SpringAI.Repository;

import com.Spring.AI.FirstProject.SpringAI.Entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ItemEntityRepository extends JpaRepository<ItemEntity, Long>
{
    // Example: Finding items priced over a certain amount to suggest premium sales
    List<ItemEntity> findByPriceGreaterThan(BigDecimal price);

    // If you have a stock column in the actual SQL table:
    @Query(value = "SELECT * FROM tbl_items WHERE stock_quantity < 5", nativeQuery = true)
    List<ItemEntity> findLowStockItems();
}
