package com.example.demo.brand.dto;

import jakarta.validation.constraints.NotEmpty;

public record BrandDto(
   @NotEmpty(message = "name is required")
   String brand_name,

   @NotEmpty(message = "ascii is required")
   String brand_ascii,

   @NotEmpty(message = "category is required")
   String category_ascii
) {}
