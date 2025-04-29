package com.rohlikgroup.casestudy.service.impl;

import com.rohlikgroup.casestudy.dto.CreateOrderRequest;
import com.rohlikgroup.casestudy.dto.OrderDto;
import com.rohlikgroup.casestudy.entity.Order;
import com.rohlikgroup.casestudy.entity.OrderItem;
import com.rohlikgroup.casestudy.entity.OrderStatus;
import com.rohlikgroup.casestudy.mapper.OrderMapper;
import com.rohlikgroup.casestudy.mapper.OrderRequestMapperImpl;
import com.rohlikgroup.casestudy.repository.OrderRepository;
import com.rohlikgroup.casestudy.repository.ProductRepository;
import com.rohlikgroup.casestudy.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;
    private final OrderRequestMapperImpl orderRequestMapper;


    @Override
    @Transactional
    public OrderDto createOrder(CreateOrderRequest orderRequest) {
        //TODO: implement here

        return null;
    }

    @Override
    @Transactional
    public OrderDto cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException("Order not found"));

        if (order.getStatus() == OrderStatus.CANCELED || order.getStatus() == OrderStatus.PAID) {
            throw new IllegalStateException("Cannot cancel order in status " + order.getStatus());
        }

        for (OrderItem item : order.getOrderItems()) {
            productRepository.releaseStock(item.getProduct().getId(), item.getQuantity());
        }

        order.setStatus(OrderStatus.CANCELED);

        return orderMapper.map(order);
    }

    @Override
    @Transactional
    public OrderDto setOrderPaid(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException("Order not found"));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new IllegalStateException("Only pending orders can be paid for");
        }

        order.setStatus(OrderStatus.PAID);
        order.setPaidAt(LocalDateTime.now());
        return orderMapper.map(order);
    }

    @Override
    @Transactional
    public OrderDto createOrder(OrderDto orderRequest) {

        Order newOrderRequest = orderRequestMapper.mapOrderDtoToEntity(orderRequest);
        validateRequest(newOrderRequest);

        try {
            Map<String, Integer> reservedStock = reserveStock(newOrderRequest);
            Order newOrderEntity = createOrderEntity(newOrderRequest);
            return orderMapper.map(orderRepository.save(newOrderEntity));
        } catch (Exception e) {
            // release stock in case of error
            releaseStock(reservedStock);
            throw e;
        }
    }

    private void validateRequest(Order newOrderRequest) {
        validateProductCount(newOrderRequest);
    }

    private void validateProductCount(Order newOrderRequest) {
        // create map with all product ids and their count
        // throw validation error in case any count > 1
    }

    private Map<String, Integer> reserveStock(Order newOrderRequest) {
        // create map with all product ids and their count
        // call productRepository.reserveStock(productId, count) for each product, make sure to throw exception if not enough stock
        return null;
    }

    private void releaseStock(Map<String, Integer> reservedStock) {
        // release all stock which was previously reserved, as per the input
    }

    private Order createOrderEntity(Order newOrderRequest) {
        Order orderEntity = new Order();

        orderEntity.setStatus(OrderStatus.PENDING);
        orderEntity.setOrderItems(newOrderRequest.getOrderItems().stream().map(this::createOrderItemEntity).toList());

        return orderEntity;
    }

    private List<OrderItem> createOrderItemEntity(OrderItem newOrderItemRequest, Order orderEntity) {
        // TODO
        OrderItem orderItemEntity = new OrderItem();

        orderItemEntity.setOrder(orderEntity);
        orderItemEntity.setProduct(newOrderItemRequest.getProduct());
        orderItemEntity.setQuantity(newOrderItemRequest.getQuantity());

        return orderItemEntity;
    }
}
