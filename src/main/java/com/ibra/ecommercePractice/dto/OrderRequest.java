package com.ibra.ecommercePractice.dto;

import com.ibra.ecommercePractice.enums.OrderStatus;
import com.ibra.ecommercePractice.model.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.User;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private BigDecimal totalPrice; //will be factored
    private List<OrderItemRequest> orderItemList;
    private Payment paymentInfo;
//    private OrderStatus orderStatus; //will be set, initially it is PENDING
}
