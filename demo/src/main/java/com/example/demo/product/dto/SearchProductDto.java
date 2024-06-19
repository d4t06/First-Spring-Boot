package com.example.demo.product.dto;

import com.example.demo.default_storage.dto.DefaultStorageDto;

public record SearchProductDto(
        Long id,

        String product_name,

        String product_name_ascii,

        String image_url,

        boolean installment,

        DefaultStorageDto default_storage

) {
}