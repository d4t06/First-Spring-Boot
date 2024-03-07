package com.example.demo.brand.dto;

import jakarta.validation.constraints.NotEmpty;

public record UpdateBrandDto (
   @NotEmpty(message = "name is required")
   String brand_name,

   @NotEmpty(message = "ascii is required")
   String brand_ascii
) {}
