package com.example.demo.combine.dto;

import jakarta.validation.constraints.NotEmpty;

public record CombineDto(
        Long id,

        @NotEmpty(message = "color_ascii is required") Long color_id,

        @NotEmpty(message = "storage_ascii is required") Long storage_id,

        @NotEmpty(message = "product_ascii is required") Long product_id,

        @NotEmpty(message = "price is required") int price,

        @NotEmpty(message = "quantity is required") int quantity) {
}
