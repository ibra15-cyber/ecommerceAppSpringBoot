package com.ibra.ecommercePractice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ibra.ecommercePractice.enums.OrderStatus;
import com.ibra.ecommercePractice.model.Order;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    private int status; //response status
    private String message;
    private final LocalDateTime timestamp = LocalDateTime.now();

    private String token;
    private String role;
    private String expirationTime;

    private int totalPage;
    private Long totalElement;

    private AddressDto addressDto;

    private UserDto userDto;
    private List<UserDto> userDtoList;
    private CategoryDto categoryDto;
    private List<CategoryDto> categoryDtoList;
    private ProductDto productDto;
    private List<ProductDto> productListDto;
    private OrderDto orderDto;
    private List<OrderDto> orderDtoList;

    private OrderStatus orderStatus;

    private String paymentUrl;
    private String paymentMethod;

}
