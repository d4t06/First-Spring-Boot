package com.example.demo.storage.dto;

import jakarta.validation.constraints.NotEmpty;

public record StorageDto(
        Long id,

        @NotEmpty(message = "storage is required") String storage_name,

        @NotEmpty(message = "storage_ascii is required") String storage_name_ascii,

        @NotEmpty(message = "product_ascii is required") Long product_id,

        DefaultStorageCombineDto default_combine
        ) {
}
