package com.example.demo.price_range.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PriceRangeDto(
        Long id,

        @NotNull(message = "category_id is required")
        Long category_id,

        @NotNull(message = "from is required")
        Byte from_price,

        @NotNull(message = "to is required")
        Byte to_price,

        @NotEmpty(message = "label is required")
        String label


        ) {}
