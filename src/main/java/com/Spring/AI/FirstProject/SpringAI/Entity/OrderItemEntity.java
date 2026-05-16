package com.Spring.AI.FirstProject.SpringAI.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

@Entity
@Table(name = "tbl_orderItems") // Matches your real POS database
@Getter
@ToString
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String itemId;
    private String name;
    private Double price;
    private Integer quantity;
}