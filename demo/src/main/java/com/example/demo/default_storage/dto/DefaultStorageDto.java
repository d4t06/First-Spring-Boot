package com.example.demo.default_storage.dto;

import com.example.demo.storage.dto.StorageDto;

import jakarta.validation.constraints.NotEmpty;

public record DefaultStorageDto(
        @NotEmpty(message = "product_ascii is required") Long product_id,
        Long storage_id,
        StorageDto storage) {
}
