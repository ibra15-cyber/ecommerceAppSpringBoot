//package com.ibra.ecommercePractice.service.impl;
//
//import com.ibra.ecommercePractice.dto.OrderDto;
//import com.ibra.ecommercePractice.dto.OrderItemDto;
//import com.ibra.ecommercePractice.dto.OrderRequest;
//import com.ibra.ecommercePractice.dto.Response;
//import com.ibra.ecommercePractice.enums.OrderStatus;
//import com.ibra.ecommercePractice.exception.NotFoundException;
//import com.ibra.ecommercePractice.mapper.EntityDtoMapper;
//import com.ibra.ecommercePractice.model.Order;
//import com.ibra.ecommercePractice.model.OrderItem;
//import com.ibra.ecommercePractice.model.Product;
//import com.ibra.ecommercePractice.model.User;
//import com.ibra.ecommercePractice.repository.OrderItemRepository;
//import com.ibra.ecommercePractice.repository.OrderRepository;
//import com.ibra.ecommercePractice.repository.ProductRepository;
//import com.ibra.ecommercePractice.service.interf.OrderItemService;
//import com.ibra.ecommercePractice.service.interf.UserService;
//import com.ibra.ecommercePractice.specification.OrderItemSpecification;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@Slf4j
//public class OrderItemServiceImpl implements OrderItemService {
//
//    private final UserService userService;
//    private final ProductRepository productRepository;
//    private final OrderRepository orderRepository;
//    private final EntityDtoMapper entityDtoMapper;
//    private final OrderItemRepository orderItemRepository;
//
//    public OrderItemServiceImpl(UserService userService, ProductRepository productRepository, OrderRepository orderRepository, EntityDtoMapper entityDtoMapper, OrderItemRepository orderItemRepository) {
//        this.userService = userService;
//        this.productRepository = productRepository;
//        this.orderRepository = orderRepository;
//        this.entityDtoMapper = entityDtoMapper;
//        this.orderItemRepository = orderItemRepository;
//    }
//
//    @Override
//    public Response placeOrder(OrderRequest orderRequest) {
//
//        System.out.println("order got here .....................");
//        User user = userService.getLoginUser();
//
////        System.out.println("our user is ............." + user);
//
//
//
//        List<OrderItem> orderItems = orderRequest.getOrderItemList().stream().map(
//                orderItemRequest -> {
//                    Product product = productRepository.findById(orderItemRequest.getProductId())
//                            .orElseThrow(()-> new NotFoundException("Product not found"));
//
//                    OrderItem orderItem = new OrderItem();
//                    orderItem.setUser(user);
//                    orderItem.setProduct(product);
//                    orderItem.setName(product.getName());
//                    orderItem.setQuantity(orderItemRequest.getQuantity());
//                    System.out.println(orderItem.getName());
//                    orderItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(orderItemRequest.getQuantity()))); //set price according to the quantity
//                    orderItem.setOrderStatus(OrderStatus.PENDING);
//
////                    System.out.println(orderItem.getUser());
//
//                    return orderItem;
//                }
//        ).collect(Collectors.toList());
//
//
//        //calculate the total price
//        BigDecimal totalPrice = orderRequest.getTotalPrice() != null && orderRequest.getTotalPrice().compareTo(BigDecimal.ZERO) > 0
//                            ? orderRequest.getTotalPrice()
//                            : orderItems.stream().map(OrderItem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
//
//        //create order entity
//        Order order = new Order();
//        order.setOrderItemList(orderItems);
//        order.setTotalPrice(totalPrice);
//
//        //set the reference in each order
//        orderItems.forEach(orderItem -> orderItem.setOrder(order));
//
//        orderRepository.save(order);
//
//
//        return Response.builder()
//                .status(201)
//                .message("Order created successfully")
//                .build();
//    }
//
//    @Override
//    public Response updateOrderItemStatus(Long orderItemId, OrderStatus status) {
//        OrderItem orderItem = orderItemRepository.findById(orderItemId).orElseThrow(()-> new NotFoundException("Order not found"));
//
////        orderItem.setStatus(OrderStatus.valueOf(status.toUpperCase()));
//        orderItem.setOrderStatus(status);
//
//        orderItemRepository.save(orderItem);
//
//        return Response.builder()
//                .status(200)
//                .orderStatus(status)
//                .message("order updated successfully")
//                .build();
//    }
//
//
//    @Override
//    public Response filterOrderItems(OrderStatus orderStatus, LocalDateTime startDate, LocalDateTime endDate, Long itemId, Pageable pageable) {
//
//        Specification<OrderItem> spec = Specification.where(OrderItemSpecification.hasStatus(orderStatus))
//                .and(OrderItemSpecification.createdBetween(startDate, endDate))
//                .and(OrderItemSpecification.hasItemId(itemId));
//
//        Page<OrderItem> orderItemPage = orderItemRepository.findAll(spec, pageable);
//
//        if(orderItemPage.isEmpty()){
//            throw new NotFoundException("Order Item Not Found");
//        }
//
//        List<OrderItemDto> orderItemDtos = orderItemPage.stream().map(entityDtoMapper::mapOrderItemToOrderItemDto).collect(Collectors.toList());
//
//        return Response.builder()
//                .status(200)
//                .orderItemDtoList(orderItemDtos)
//                .totalPage(orderItemPage.getTotalPages())
//                .totalElement(orderItemPage.getTotalElements())
//                .build();
//    }
//
//    @Override
//    public Response getAllOrders() {
////        User user = userService.getLoginUser();
//
//        List<OrderItem> orderItemsList =  orderItemRepository.findAll();
//
//        List<OrderItemDto> orderItemsDtoList = orderItemsList.stream().map(entityDtoMapper::mapOrderItemToOrderItemDto).collect(Collectors.toList());
//
//        return Response.builder()
//                .orderItemDtoList(orderItemsDtoList)
//                .build();
//    }
//}
