package com.ibra.ecommercePractice.repository;

import com.ibra.ecommercePractice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCategoryId(Long categoryId);
    List<Product> findByNameOrDescriptionContaining(String name, String description);
}
