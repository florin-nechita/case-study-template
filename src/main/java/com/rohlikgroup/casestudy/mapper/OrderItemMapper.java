package com.rohlikgroup.casestudy.mapper;

import com.rohlikgroup.casestudy.dto.OrderItemDto;
import com.rohlikgroup.casestudy.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(target = "order", ignore = true)
    OrderItem map(OrderItemDto orderItemDto);

    OrderItemDto map(OrderItem orderItem);
}
