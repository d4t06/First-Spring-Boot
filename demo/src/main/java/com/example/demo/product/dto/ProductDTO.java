package com.example.demo.product.dto;

import java.util.List;

import com.example.demo.default_storage.dto.DefaultStorageDto;
import com.example.demo.storage.dto.StorageDto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ProductDTO(
        Long id,

        @NotEmpty(message = "name is required") String product_name,

        @NotEmpty(message = "name is required") String product_name_ascii,

        @NotNull(message = "category is required") Long category_id,

        @NotNull(message = "brand is required") Long brand_id,

        String image_url,

        boolean installment,

        List<StorageDto> storages,

        DefaultStorageDto default_storage) {
}
