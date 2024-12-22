package com.ibra.ecommercePractice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ibra.ecommercePractice.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
//    private Long id;
//    private String name;
//    private String email;
//    private String password;
//    private String phoneNumber;
//    //    @Enumerated
//    private UserRole role;
////    private List<OrderDto> orderDtoList;
//    private List<OrderItemDto> orderItemDtoList;
//    private AddressDto addressDto;
//    private LocalDateTime createdAt;

    private Long id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    //    @Enumerated
    private UserRole role;
    //    private List<OrderDto> orderDtoList;
    private List<OrderDto> orderDtoList;
    private AddressDto addressDto; //you could restrict fields you deem sensitive
    private LocalDateTime createdAt;
}