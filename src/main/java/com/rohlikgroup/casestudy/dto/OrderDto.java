package com.rohlikgroup.casestudy.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDto(Long id,
                       @NotBlank
                       String status,
                       @Size(min = 1)
                       List<OrderItemDto> orderItems,
                       LocalDateTime paidAt) {

}
