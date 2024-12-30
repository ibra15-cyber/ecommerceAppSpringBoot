package com.ibra.ecommercePractice.model;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Table(name="products")
//@RedisHash("Product")
@Entity
public class Product implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String imageUrl;
    private BigDecimal price;

    @ManyToOne( fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinColumn(name="category_id")
    @JoinColumn(name = "category_id", nullable = false)
//    @OnDelete(action = OnDeleteAction.CASCADE) //this deletes the category when the product is deleted
//    @JsonIgnore
    private Category category;

    @Column(name="created_at")
    private final LocalDateTime createdAt = LocalDateTime.now();
}
