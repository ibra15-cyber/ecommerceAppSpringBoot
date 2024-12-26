package com.ibra.ecommercePractice.repository;

import com.ibra.ecommercePractice.dto.Response;
import com.ibra.ecommercePractice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
}
