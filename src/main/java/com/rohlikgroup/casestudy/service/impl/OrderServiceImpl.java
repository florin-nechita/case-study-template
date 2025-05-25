package com.rohlikgroup.casestudy.service.impl;

import com.rohlikgroup.casestudy.dto.CreateOrderRequest;
import com.rohlikgroup.casestudy.dto.OrderDto;
import com.rohlikgroup.casestudy.dto.OrderItemDto;
import com.rohlikgroup.casestudy.dto.ProductDto;
import com.rohlikgroup.casestudy.entity.Order;
import com.rohlikgroup.casestudy.entity.OrderItem;
import com.rohlikgroup.casestudy.entity.OrderStatus;
import com.rohlikgroup.casestudy.entity.Product;
import com.rohlikgroup.casestudy.mapper.OrderMapper;
import com.rohlikgroup.casestudy.repository.OrderRepository;
import com.rohlikgroup.casestudy.repository.ProductRepository;
import com.rohlikgroup.casestudy.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

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
        validateRequest(orderRequest);

        Map<String, Integer> reservedStock = new HashMap<>();
        try {
            reservedStock = reserveStock(orderRequest);
            Order newOrderEntity = createOrderEntity(orderRequest);
            return orderMapper.map(orderRepository.save(newOrderEntity));
        } catch (Exception e) {
            // release stock in case of error
            releaseStock(reservedStock);
            throw e;
        }
    }

    private void validateRequest(OrderDto orderRequest) {
        validateProductCount(orderRequest);
    }

    private void validateProductCount(OrderDto orderRequest) {
        Map<Long, List<ProductDto>> productsById = orderRequest.orderItems().stream().map(OrderItemDto::product).collect(Collectors.groupingBy(ProductDto::id));
        productsById.entrySet().stream().filter(productsForId -> productsForId.getValue().size() > 1).findAny().ifPresent(this::throwProductCountValidationError);
    }

    private void throwProductCountValidationError(Map.Entry<Long, List<ProductDto>> productsForId) {
        throw new IllegalArgumentException("Product with id: " + productsForId.getKey() + " must appear only once. Found: " + productsForId.getValue().size() + " occurrences.");
    }

    @Transactional
    private Map<String, Integer> reserveStock(OrderDto newOrderRequest) {
        // create map with all product ids and their count
        // call productRepository.reserveStock(productId, count) for each product, make sure to throw exception if not enough stock
        return null;
    }

    private void releaseStock(Map<String, Integer> reservedStock) {
        // release all stock which was previously reserved, as per the input
    }

    private Order createOrderEntity(OrderDto newOrderRequest) {
        Order orderEntity = new Order();

        orderEntity.setStatus(OrderStatus.PENDING);
        orderEntity.setOrderItems(createOrderItemEntities(newOrderRequest));

        return orderEntity;
    }

    private List<OrderItem> createOrderItemEntities(OrderDto orderRequest) {
        return orderRequest.orderItems().stream().map(oi -> createSingleOrderItemEntity(oi, orderRequest)).toList();
    }

    private OrderItem createSingleOrderItemEntity(OrderItemDto newOrderItemRequest, OrderDto orderEntity) {
        OrderItem orderItemEntity = new OrderItem();

        orderItemEntity.setProduct(createProduct(newOrderItemRequest.product()));
        orderItemEntity.setQuantity(newOrderItemRequest.quantity());

        return orderItemEntity;
    }

    private Product createProduct(ProductDto product) {
        return null;
    }
}
