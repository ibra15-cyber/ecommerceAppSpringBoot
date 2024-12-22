package com.ibra.ecommercePractice.service.interf;


import com.ibra.ecommercePractice.dto.OrderFilterRequest;
import com.ibra.ecommercePractice.dto.OrderRequest;
import com.ibra.ecommercePractice.dto.Response;
import com.ibra.ecommercePractice.enums.OrderStatus;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface OrderService {
    Response placeOrder(OrderRequest orderRequest);
    Response updateOrderStatus(Long orderItemId, OrderStatus status);
//    Response filterOrderItems(OrderStatus ordStatus, LocalDateTime startDate, LocalDateTime endTime, Long itemId, int pageNumber,int pageSize);
    Response filterOrderItems(OrderFilterRequest orderFilterRequest);
    Response getAllOrders();
}
