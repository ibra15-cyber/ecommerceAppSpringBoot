package com.ibra.ecommercePractice.mapper;

import com.ibra.ecommercePractice.dto.*;
import com.ibra.ecommercePractice.model.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntityDtoMapper {

// user entity to user DTO
    public UserDto mapUserToUserDto(User user){
        UserDto userDto = new UserDto();

        //take the user from the db and scrap the properties that are non sensitive to the userDto
        //it can still have a UserDto but return User in response (not do that User might have sensitive data)
        userDto.setId(user.getId());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole());
        userDto.setName(user.getName());

        return userDto;
    }

    //address to address dto
    public AddressDto mapAddressToAddressDto(Address address){

        AddressDto addressDto = new AddressDto();
        addressDto.setId(address.getId());
        addressDto.setCity(address.getCity());
        addressDto.setState(address.getState());
        addressDto.setStreet(address.getStreet());
        addressDto.setCountry(address.getCountry());
        addressDto.setZipCode(address.getZipCode());

        return addressDto;

    }


    public CategoryDto mapCategoryToCategoryDto(Category category){
        CategoryDto categoryDto = new CategoryDto();

        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());

        return categoryDto;
    }


//    public OrderItemDto mapOrderItemToOrderItemDto(OrderItem orderItem){
//        OrderItemDto orderItemDto = new OrderItemDto();
//
//        orderItemDto.setId(orderItem.getId());
//        orderItemDto.setQuantity(orderItem.getQuantity());
//        orderItemDto.setPrice(orderItem.getPrice());
//        orderItemDto.setStatus(orderItem.getOrderStatus());
////        orderItemDto.setUserDto(mapUserToUserDto(orderItem.getUser()));
//        orderItemDto.setUserId(orderItem.getUser().getId());
//        orderItemDto.setUserName(orderItem.getUser().getName());
//        orderItemDto.setProductName(orderItem.getProduct().getName());
//        orderItemDto.setCreatedAt(orderItem.getCreatedAt());
//
//        return orderItemDto;
//    }

    public OrderItemDto mapOrderItemToOrderItemDto(OrderItem orderItem){
        OrderItemDto orderItemDto = new OrderItemDto();

        orderItemDto.setId(orderItem.getId());
        orderItemDto.setQuantity(orderItem.getQuantity());
        orderItemDto.setPrice(orderItem.getPrice());
//        orderItemDto.setStatus(orderItem.getOrderStatus());
//        orderItemDto.setUserDto(mapUserToUserDto(orderItem.getUser()));
//        orderItemDto.setUserId(orderItem.getUser().getId());
//        orderItemDto.setUserName(orderItem.getUser().getName());
        orderItemDto.setProductName(orderItem.getProduct().getName());
        orderItemDto.setCreatedAt(orderItem.getCreatedAt());
        return orderItemDto;
    }

    public ProductDto mapProductToProductDto(Product product){
        ProductDto productDto = new ProductDto();

        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImgUrl(product.getImageUrl());

        return productDto;
    }


    //map user to userDto plus address
    //map user to userDto and Address
    //for a user map it to userDto
    //if user has an address pass the address to addressDto
    //and set it to the userDto
    public UserDto mapUserToUserDtoPlusAddress(User user){
        UserDto userDto = mapUserToUserDto(user);

        if(user.getAddress() != null) {
            AddressDto addressDto = mapAddressToAddressDto(user.getAddress());
            userDto.setAddressDto(addressDto);
         }

        return userDto;
    }





//    //map orderItem to orderDto plus product
    public OrderItemDto mapOrderItemToOrderItemDtoPlusProduct(OrderItem orderItem){
        OrderItemDto orderItemDto = mapOrderItemToOrderItemDto(orderItem);

        if(orderItem.getProduct() != null){
            //pass product to productDto from the orderItem
            ProductDto productDto = mapProductToProductDto(orderItem.getProduct());
            orderItemDto.setProductDto(productDto);
        }

        return orderItemDto;
    }

//    //my mapOrderToOrderDtoPlusOrderItem
//    public OrderDto mapOrderToOrderDtoPlusOrderItem(Order order){
//        OrderDto orderDto
//        return orderDto;
//    }
//
//    //map orderItem to orderDto plus product and User
//    public OrderItemDto mapOderItemToOrderDtoPlusProductAndUser(OrderItem orderItem){
//        OrderItemDto orderItemDto = mapOrderItemToOrderItemDtoPlusProduct(orderItem);
//
//        if(orderItem.getUser() != null){
//            UserDto userDto = mapUserToUserDtoPlusAddress(orderItem.getUser());
//            orderItemDto.setUserDto(userDto);
//        }
//
//        return orderItemDto;
//    }



//
//    //me changing the User association to OrderItem to Order
//    //map orderItem to orderDto plus product and User
//    public OrderDto mapOrderToOrderDtoPlusOrderItemAndUser(Order order){
//        OrderDto orderDto = mapOrderToOrderDtoPlusProduct(order);
//
//        if(order.getUser() != null){
//            UserDto userDto = mapUserToUserDtoPlusAddress(order.getUser());
//            orderDto.setUserDto(userDto);
//        }
//
//        return orderDto;
//    }

//    private OrderDto mapOrderToOrderDtoPlusProduct(Order order) {
//      OrderDto orderDto = mapOrderToOrderDto(order);
//
//      if (order.get)
//    }

//    private OrderDto mapOrderToOrderDto(Order order) {
//        OrderDto orderDto = new OrderDto();
//        orderDto.setTotalPrice(order.getTotalPrice());
//        orderDto.setOrderDtoStatus(order.getStatus());
//        orderDto.setUserDto(mapUserToUserDto(order.getUser()));
//        return orderDto;
//
//    }

    //user to userDto with address and orderItems History
//    public UserDto mapUsertoUserDtoPlusAddressAndOrderHistory(User user){
//        UserDto userDto =  mapUserToUserDtoPlusAddress(user);
//
//        if(user.getOrderItemList() != null && !user.getOrderItemList().isEmpty()){
//            userDto.setOrderItemDtoList(user.getOrderItemList()
//                    .stream()
//                    .map(this::mapOrderItemToOrderItemDtoPlusProduct)
//                    .collect(Collectors.toList()));
//        }
//        return userDto;
//    }

    public UserDto mapUsertoUserDtoPlusAddressAndOrderHistory(User user){
        UserDto userDto =  mapUserToUserDtoPlusAddress(user);

        if(user.getOrderList() != null && !user.getOrderList().isEmpty()){
            userDto.setOrderDtoList(user.getOrderList()
                    .stream()
                    .map(this::mapOrderToOrderDto)
                    .collect(Collectors.toList()));
        }
        return userDto;
    }

    public OrderDto mapOrderToOrderDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setTotalPrice(order.getTotalPrice());
//        orderDto.setOrderItemDtoList(mapOrderItemToOrderItemDto(order.getOrderItemList()));
        //order.getStatus returns OrderStatus same as getOrderDtoStatus
        orderDto.setOrderDtoStatus(order.getStatus());
        //this has userDto and the one in paramthesis has user therefore converstion
//        orderDto.setUserDto(mapUserToUserDto(order.getUser()));
        orderDto.setUsername(mapUserToUserDto(order.getUser()).getName());
        orderDto.setId(order.getId());
        orderDto.setTotalPrice(order.getTotalPrice());

        //same here, orderItemList returns List<OrderItem> but oderItemDtoList returns List<OrderItemDto>
        orderDto.setOrderItemDtoList(order.getOrderItemList().stream().map(this::mapOrderItemToOrderItemDto).collect(Collectors.toList()));

        return orderDto;
    }



    //user to userDto with address and orderItems History
//    public UserDto mapUsertoUserDtoPlusAddressAndOrderHistory(User user){
//        UserDto userDto =  mapUserToUserDtoPlusAddress(user);
//
//        if(user.getOrderList() != null && !user.getOrderList().isEmpty()){
//            userDto.setOrderDtoList(user.getOrderList()
//                    .stream()
//                    .map(this::mapOrderItemToOrderItemDtoPlusProduct)
//                    .collect(Collectors.toList()));
//        }
//        return userDto;
//    }
}
