package com.rohlikgroup.casestudy.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderItemDto(
        Long id,
        @NotNull
        ProductDto product,
        @NotNull
        @Min(1)
        Integer quantity) {

}

