package com.rohlikgroup.casestudy.repository;

import com.rohlikgroup.casestudy.entity.Order;
import com.rohlikgroup.casestudy.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    boolean existsByOrderItemsProductIdAndStatusNotIn(Long productId, Set<OrderStatus> statuses);
}
