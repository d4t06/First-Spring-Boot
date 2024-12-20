package com.example.demo.cart_item.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CartItemDto(Long id,
        @NotEmpty(message = "username is required") String username,
        @NotNull(message = "color_id is required") Long color_id,
        @NotNull(message = "storage_id is required") Long storage_id,
        @NotNull(message = "amount is required") int amount,
        @NotNull(message = "product_id is required") Long product_id,

        CartProductDto product) {

}
