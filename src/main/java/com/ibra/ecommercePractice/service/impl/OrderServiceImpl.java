package com.ibra.ecommercePractice.service.impl;

import com.ibra.ecommercePractice.dto.*;
import com.ibra.ecommercePractice.enums.OrderStatus;
import com.ibra.ecommercePractice.exception.NotFoundException;
import com.ibra.ecommercePractice.mapper.EntityDtoMapper;
import com.ibra.ecommercePractice.model.Order;
import com.ibra.ecommercePractice.model.OrderItem;
import com.ibra.ecommercePractice.model.Product;
import com.ibra.ecommercePractice.model.User;
import com.ibra.ecommercePractice.repository.OrderItemRepository;
import com.ibra.ecommercePractice.repository.OrderRepository;
import com.ibra.ecommercePractice.repository.ProductRepository;
import com.ibra.ecommercePractice.service.interf.OrderItemService;
import com.ibra.ecommercePractice.service.interf.OrderService;
import com.ibra.ecommercePractice.service.interf.UserService;
import com.ibra.ecommercePractice.specification.OrderItemSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final UserService userService;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final EntityDtoMapper entityDtoMapper;
//    private final OrderItemRepository orderItemRepository;

    public OrderServiceImpl(UserService userService, ProductRepository productRepository, OrderRepository orderRepository, EntityDtoMapper entityDtoMapper, OrderItemRepository orderItemRepository) {
        this.userService = userService;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.entityDtoMapper = entityDtoMapper;
//        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public Response placeOrder(OrderRequest orderRequest) {

        User user = userService.getLoginUser();

//        System.out.println("our user is ............." + user);

        List<OrderItem> orderItems = orderRequest.getOrderItemList().stream().map(
                orderItemRequest -> {
                    Product product = productRepository.findById(orderItemRequest.getProductId())
                            .orElseThrow(()-> new NotFoundException("Product not found"));

                    OrderItem orderItem = new OrderItem();
//                    orderItem.setUser(user);
                    orderItem.setProduct(product);
                    orderItem.setName(product.getName());
                    orderItem.setQuantity(orderItemRequest.getQuantity());
                    orderItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(orderItemRequest.getQuantity()))); //set price according to the quantity
//                    orderItem.setOrderStatus(OrderStatus.PENDING);

//                    System.out.println(orderItem.getUser());

                    return orderItem;
                }
        ).collect(Collectors.toList());


        //calculate the total price; the first condition return false because we aint passing any total value
        //therefore the second condition hold, aka it for each orderItem, it get the price and add them
        BigDecimal totalPrice = orderRequest.getTotalPrice() != null && orderRequest.getTotalPrice().compareTo(BigDecimal.ZERO) > 0
                            ? orderRequest.getTotalPrice()
                            : orderItems.stream().map(OrderItem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        //create order entity
        Order order = new Order();
        order.setOrderItemList(orderItems);
        order.setTotalPrice(totalPrice);
        order.setUser(user);
//        order.setStatus(orderRequest.getOrderStatus());
        order.setStatus(OrderStatus.PENDING);

        //set each of the orderItems to their respective order
        orderItems.forEach(orderItem -> orderItem.setOrder(order));

        orderRepository.save(order);


        return Response.builder()
                .status(201)
                .message("Order created successfully")
                .build();
    }

    @Override
    public Response updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new NotFoundException("Order not found"));

//        orderItem.setStatus(OrderStatus.valueOf(status.toUpperCase()));
        order.setStatus(status);

        orderRepository.save(order);

        return Response.builder()
                .status(200)
                .orderStatus(status)
                .message("order updated successfully")
                .build();
    }


    @Override
//    public Response filterOrderItems(OrderStatus status, LocalDateTime startDate, LocalDateTime endDate, Long itemId, int pageNumber, int pageSize) {
        public Response filterOrderItems(OrderFilterRequest orderFilterRequest) {

        //create a pagination for the return search request
        Pageable pageable = PageRequest.of(orderFilterRequest.getPageNumber(), orderFilterRequest.getPageSize(), Sort.by(Sort.Direction.DESC, "id"));

//        System.out.println(".....................order status" + status);

        //if we pass a status string in the request, return the string representation and upper it then returns the value of it else, keep the status null
        OrderStatus orderStatus = orderFilterRequest.getStatus() != null ? OrderStatus.valueOf(orderFilterRequest.getStatus().toString().toUpperCase()): null;

        //initializing our specification and chaining it where status is passed, or start and enddate or itemid
        Specification<Order> spec = Specification.where(OrderItemSpecification.hasStatus(orderStatus))
                .and(OrderItemSpecification.createdBetween(orderFilterRequest.getStartDate(), orderFilterRequest.getEndDate()))
                .and(OrderItemSpecification.hasItemId(orderFilterRequest.getId()));

        //search all the order Item that fit our specification and return them as pageable (we extend JpaSpecificationExecutor<OrderItem>  in the repository)
        //which has a findAll implementation that takes a specification and pageable
        Page<Order> orderListPage = orderRepository.findAll(spec, pageable);

        if(orderListPage.isEmpty()){
            throw new NotFoundException("Order Item Not Found");
        }

        List<OrderDto> orderDtoList = orderListPage
                .stream()
                .map(entityDtoMapper::mapOrderToOrderDto)
                .collect(Collectors.toList());

        return Response.builder()
                .status(200)
                .orderDtoList(orderDtoList)
                .totalPage(orderListPage.getTotalPages())
                .totalElement(orderListPage.getTotalElements())
                .build();
    }

    @Override
    public Response getAllOrders() {
//        User user = userService.getLoginUser();

        List<Order> orderList =  orderRepository.findAll();

        List<OrderDto> orderDtoList = orderList
                .stream()
                .map(entityDtoMapper::mapOrderToOrderDto).collect(Collectors.toList());

        return Response.builder()
                .orderDtoList(orderDtoList)
                .build();
    }
}
