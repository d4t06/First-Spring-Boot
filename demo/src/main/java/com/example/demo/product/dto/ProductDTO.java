package com.example.demo.product.dto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ProductDTO(
        Long id,

        @NotEmpty(message = "name is required")
        String product_name,
        
        @NotEmpty(message = "ascii is required")
        String product_ascii,
        
        @NotNull(message = "category is required")
        Long category_id,
        
        @NotNull(message = "brand is required")
        Long brand_id,
        
        String image_url,

        @NotNull(message = "price is required")
        int price,

        boolean installment
        ) {
}
