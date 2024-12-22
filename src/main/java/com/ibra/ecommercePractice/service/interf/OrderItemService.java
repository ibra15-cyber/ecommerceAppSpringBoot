package com.ibra.ecommercePractice.service.interf;

import com.ibra.ecommercePractice.dto.OrderFilterRequest;
import com.ibra.ecommercePractice.dto.OrderRequest;
import com.ibra.ecommercePractice.dto.Response;
import com.ibra.ecommercePractice.enums.OrderStatus;
import com.ibra.ecommercePractice.specification.OrderItemSpecification;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface OrderItemService {
    Response placeOrder(OrderRequest orderRequest);
    Response updateOrderItemStatus(Long orderItemId, OrderStatus status);
//    Response filterOrderItems(OrderStatus orderStatus, LocalDateTime startDate, LocalDateTime endTime, Long itemId, Pageable pageable);
    Response filterOrderItems(OrderFilterRequest orderFilterRequest);
    Response getAllOrders();
}
