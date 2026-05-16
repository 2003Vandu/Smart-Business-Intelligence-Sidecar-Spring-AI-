package com.Spring.AI.FirstProject.SpringAI.Repository;

import com.Spring.AI.FirstProject.SpringAI.Entity.ItemEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ItemEntityRepository extends JpaRepository<ItemEntity, Long>
{
    // Items above price
    List<ItemEntity> findByPriceGreaterThan(BigDecimal price);

    // fetch only top N items from DB (no findAll() in memory)
    @Query("SELECT i FROM ItemEntity i ORDER BY i.createdAt DESC")
    List<ItemEntity> findTopItems(Pageable pageable);

    // Total inventory value
    @Query("SELECT SUM(i.price) FROM ItemEntity i")
    BigDecimal getTotalInventoryValue();

    // Items by category name
    @Query("SELECT i FROM ItemEntity i WHERE i.category.name = :categoryName")
    List<ItemEntity> findByCategoryName(String categoryName);

    //  Count items per category
    @Query("SELECT i.category.name, COUNT(i) FROM ItemEntity i GROUP BY i.category.name")
    List<Object[]> countItemsPerCategory();
}
