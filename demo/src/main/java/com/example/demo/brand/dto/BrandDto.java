package com.example.demo.brand.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record BrandDto(
            Long id,

            @NotEmpty(message = "brand_name is required") String brand_name,

            @NotEmpty(message = "brand_name_ascii is required") String brand_name_ascii,

            @NotNull(message = "category_id is required") Long category_id) {
}
