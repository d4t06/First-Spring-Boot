package com.example.demo.storage.dto;

import jakarta.validation.constraints.NotEmpty;

public record StorageDto(
        Long id,

        @NotEmpty(message = "storage is required") String storage,

        @NotEmpty(message = "storage_ascii is required") String storage_ascii,

        @NotEmpty(message = "product_ascii is required") String product_ascii,

        DefaultStorageCombineDto default_combine
        ) {
}
