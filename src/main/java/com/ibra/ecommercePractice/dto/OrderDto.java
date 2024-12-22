package com.ibra.ecommercePractice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ibra.ecommercePractice.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDto {
    private Long id;
    private BigDecimal totalPrice;
    private UserDto userDto;
    private String username;
    //to not return the whole user I could set up to receive say the name String userName then I will catch and tap the user and destructure the user.getName()
    private OrderStatus orderDtoStatus;
    private List<OrderItemDto> orderItemDtoList;
    private LocalDateTime createdAt;
}
