package com.ibra.ecommercePractice.dto;

import com.ibra.ecommercePractice.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderFilterRequest {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long id;
    private OrderStatus status;
    private int pageNumber;
    private int pageSize;
}
