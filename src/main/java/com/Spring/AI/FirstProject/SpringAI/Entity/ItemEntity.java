package com.Spring.AI.FirstProject.SpringAI.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Table(name = "tbl_items")
@Getter
@ToString
public class ItemEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private BigDecimal price;
    private String description;
}
