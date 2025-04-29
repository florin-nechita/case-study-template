package com.rohlikgroup.casestudy.mapper;

import com.rohlikgroup.casestudy.dto.OrderDto;
import com.rohlikgroup.casestudy.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {

    Order map(OrderDto orderDto);

    OrderDto map(Order order);
}
