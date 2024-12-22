package com.ibra.ecommercePractice.specification;

import com.ibra.ecommercePractice.enums.OrderStatus;
import com.ibra.ecommercePractice.model.Order;
import com.ibra.ecommercePractice.model.OrderItem;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class OrderItemSpecification {
    //generate specification to filter orderItem by order Status
    public static Specification<Order> hasStatus(OrderStatus orderStatus){
        return ((root, query, criteriaBuilder) ->
                orderStatus != null ? criteriaBuilder.equal(root.get("status"), orderStatus): null
        );
    }

    //generate specification to filter orderItem by data range
    public static Specification<Order> createdBetween(LocalDateTime startDate, LocalDateTime endData) {
        return (((root, query, criteriaBuilder) -> {
            if (startDate != null && endData !=null) {
                return criteriaBuilder.between(root.get("createdAt"), startDate, endData);
            } else if (startDate !=null){
                return  criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), startDate);
            } else if (endData !=null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), endData);
            } else {
                return null;
            }
        }));
    }

    //generate specification to filter orderItem by order id
    public static Specification<Order> hasItemId(Long itemId){
        return ((root, query, criteriaBuilder) ->
                itemId != null ? criteriaBuilder.equal(root.get("id"), itemId)
                        : null
        );
    }

//    @Override
//    public Specification<OrderItem> and(Specification<OrderItem> other) {
//        return Specification.super.and(other);
//    }
//
//    @Override
//    public Specification<OrderItem> or(Specification<OrderItem> other) {
//        return Specification.super.or(other);
//    }
//
//    @Override
//    public Predicate toPredicate(Root<OrderItem> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//        return null;
//    }
}
