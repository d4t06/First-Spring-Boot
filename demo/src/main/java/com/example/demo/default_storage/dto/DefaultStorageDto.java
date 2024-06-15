package com.example.demo.default_storage.dto;

import jakarta.validation.constraints.NotEmpty;

public record DefaultStorageDto(
                @NotEmpty(message = "product_ascii is required") String product_ascii,
                Long storage_id) {
}
