//package com.ibra.ecommercePractice.controller;
//
//import com.ibra.ecommercePractice.dto.OrderRequest;
//import com.ibra.ecommercePractice.dto.Response;
//import com.ibra.ecommercePractice.enums.OrderStatus;
//import com.ibra.ecommercePractice.service.interf.OrderItemService;
//
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDateTime;
//
//@RestController
//@RequestMapping("/order")
//public class OrderItemController {
//
//    private final OrderItemService orderItemService;
//
//    public OrderItemController(OrderItemService orderItemService) {
//        this.orderItemService = orderItemService;
//    }
//
//    @PostMapping("/create")
//    public ResponseEntity<Response> placeAnOrder(@RequestBody OrderRequest orderRequest){
//        return ResponseEntity.status(HttpStatus.OK).body(orderItemService.placeOrder(orderRequest));
//    }
//
//
//    @PreAuthorize("hasAuthority('ADMIN')")
//    @PostMapping("/update-item-status/{orderItemId}")
//    public ResponseEntity<Response> updateOrderItemStatus(@PathVariable("orderItemId") Long orderItemId, @RequestParam OrderStatus orderStatus){
//        return ResponseEntity.status(HttpStatus.OK).body(orderItemService.updateOrderItemStatus(orderItemId, orderStatus));
//    }
//
//    @PreAuthorize("hasAuthority('ADMIN')")
//    @GetMapping("/")
//    public ResponseEntity<Response> getAllOrders(){
//        return ResponseEntity.status(HttpStatus.OK).body(orderItemService.getAllOrders());
//    }
//
//
//
//    @PreAuthorize("hasAuthority('ADMIN')")
//    @GetMapping("/filter")
//    public ResponseEntity<Response> filterOderItems(
//            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime startDate,
//            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime endDate,
//            @RequestParam(required = false) String status,
//            @RequestParam(required = false) Long itemId,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "1000") int size
//            ){
//
//        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
//
//        OrderStatus orderStatus = status != null ? OrderStatus.valueOf(status.toUpperCase()): null;
//
//        return ResponseEntity.status(HttpStatus.OK).body(orderItemService.filterOrderItems(orderStatus, startDate, endDate, itemId, pageable));
//    }
//
//}
