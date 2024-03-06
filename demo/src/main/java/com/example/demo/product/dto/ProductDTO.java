package com.example.demo.product.dto;
import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotEmpty;

public record ProductDTO(
          @NotEmpty(message = "name is required")
          String product_name,
          
          @NotEmpty(message = "ascii is required")
          String product_ascii,
          
          @NotEmpty(message = "category is required")
          String category_ascii,
          
          @NotEmpty(message = "brand is required")
          String brand_ascii,
          
          String image_url,
          int cur_price
          ) {
}
