package com.rohlikgroup.casestudy.mapper;

import com.rohlikgroup.casestudy.dto.OrderDto;
import com.rohlikgroup.casestudy.dto.OrderItemDto;
import com.rohlikgroup.casestudy.dto.ProductDto;
import com.rohlikgroup.casestudy.entity.Order;
import com.rohlikgroup.casestudy.entity.OrderItem;
import com.rohlikgroup.casestudy.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class OrderRequestMapperImpl {
    // TODO find out the best packaging model for the mapper
    public Order mapOrderDtoToEntity(OrderDto orderRequestDto) {
        if (orderRequestDto == null) {
            return null;
        }

        Order order = new Order();
        order.setOrderItems(mapOrderItemDtosToEntities(orderRequestDto.orderItems()));
        return order;
    }

    private List<OrderItem> mapOrderItemDtosToEntities(List<OrderItemDto> orderItemsRequestDto) {
        if (orderItemsRequestDto == null) {
            return null;
        }

        List<OrderItem> orderItems = new ArrayList<OrderItem>(orderItemsRequestDto.size());
        for (OrderItemDto orderItemDto : orderItemsRequestDto) {
            orderItems.add(mapSingleOrderItemDtoToEntity.map(orderItemDto));
        }

        return orderItems;
    }

    private OrderItem mapSingleOrderItemDtoToEntity(OrderItemDto orderItemRequestDto) {
        if (orderItemRequestDto == null) {
            return null;
        }

        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(mapProductDtoEntity(orderItemRequestDto.product()));
        orderItem.setQuantity(orderItemRequestDto.quantity());

        return orderItem;
    }

    private Product mapProductDtoEntity(ProductDto productDto) {
        if (productDto == null) {
            return null;
        }

        Product product = new Product();
        product.setId(productDto.id());

        return product;
    }
}
