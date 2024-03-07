package com.example.demo.brand.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record BrandDto(
   @NotEmpty(message = "name is required")
   String brand_name,

   @NotEmpty(message = "ascii is required")
   String brand_ascii,

   @NotNull(message = "category is required")
   Long category_id
) {}
