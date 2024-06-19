package com.example.demo.product_attribute.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ProductAttributeDto(
        Long id,
        @NotEmpty(message = "product_ascii is required") Long product_id,
        @NotNull(message = "category_attribute_id is required") Long category_attribute_id,
        @NotEmpty(message = "value is required") String value) {
}
