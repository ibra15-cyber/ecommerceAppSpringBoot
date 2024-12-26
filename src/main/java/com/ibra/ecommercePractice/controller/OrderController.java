package com.ibra.ecommercePractice.controller;

import com.ibra.ecommercePractice.dto.OrderFilterRequest;
import com.ibra.ecommercePractice.dto.OrderRequest;
import com.ibra.ecommercePractice.dto.Response;
import com.ibra.ecommercePractice.enums.OrderStatus;
import com.ibra.ecommercePractice.model.User;
import com.ibra.ecommercePractice.service.impl.UserServiceImp;
import com.ibra.ecommercePractice.service.interf.OrderItemService;
import com.ibra.ecommercePractice.service.interf.OrderService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final UserServiceImp userServiceImp;

    public OrderController(OrderService orderService, UserServiceImp userServiceImp) {
        this.orderService = orderService;
        this.userServiceImp = userServiceImp;
    }

    @PostMapping("/create")
    public ResponseEntity<Response> placeAnOrder(@RequestBody OrderRequest orderRequest){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.placeOrder(orderRequest));
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/update-item-status/{orderItemId}")
    public ResponseEntity<Response> updateOrderItemStatus(@PathVariable("orderItemId") Long orderItemId, @RequestParam OrderStatus orderStatus){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.updateOrderStatus(orderItemId, orderStatus));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/")
    public ResponseEntity<Response> getAllOrders(){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllOrders());
    }


    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/mine")
    public ResponseEntity<Response> getMyOrders(){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getMyOrders());
    }



    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/filter")
    public ResponseEntity<Response> filterOrderItems(
//            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
//            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
//            @RequestParam(required = false) OrderStatus status,
//            @RequestParam(required = false) Long itemId,
//            @RequestParam(defaultValue = "0") int pageNumber,
//            @RequestParam(defaultValue = "1000") int pageSize

            @RequestBody OrderFilterRequest orderFilterRequest
            ){
//        return ResponseEntity.status(HttpStatus.OK).body(orderService.filterOrderItems(status, startDate, endDate, itemId, pageNumber, pageSize ));
        return ResponseEntity.status(HttpStatus.OK).body(orderService.filterOrderItems(orderFilterRequest));
    }

}
