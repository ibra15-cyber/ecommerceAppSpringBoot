package com.ibra.ecommercePractice.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Table(name="products")
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String imageUrl;
    private BigDecimal price;

    @ManyToOne( fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name="category_id")
    @JoinColumn(name = "category_id", nullable = false, foreignKey = @ForeignKey(name = "FK_CATEGORY_PRODUCT"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category category;

    @Column(name="created_at")
    private final LocalDateTime createdAt = LocalDateTime.now();
}
