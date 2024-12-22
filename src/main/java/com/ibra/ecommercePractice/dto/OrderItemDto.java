package com.ibra.ecommercePractice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ibra.ecommercePractice.enums.OrderStatus;
import lombok.*;
import org.springframework.security.core.userdetails.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderItemDto {
//    private Long id;
//    private String name;
//    private int quantity;
//    private BigDecimal price;
//    private OrderStatus status;
//    private UserDto userDto;
//    private Long userId;
//    private String userName;
//    private ProductDto productDto;
//    private String productName;
//    private OrderDto orderDto;
//    private LocalDateTime createdAt;

    private Long id;
    private String name;
    private int quantity;
    private BigDecimal price;
    private OrderStatus status;
    private UserDto userDto;
    private Long userId;
    private String userName;
    private ProductDto productDto; //gives you the compete prouct obj
    private String productName; //if you only need the product name
    private OrderDto orderDto;
    private LocalDateTime createdAt;
}
