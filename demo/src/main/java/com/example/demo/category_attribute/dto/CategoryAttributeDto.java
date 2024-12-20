package com.example.demo.category_attribute.dto;

import jakarta.validation.constraints.NotEmpty;

public record CategoryAttributeDto(
        Long id,
        @NotEmpty(message = "category_id is required") Long category_id,
        @NotEmpty(message = "attribute is required") String attribute_name,
        @NotEmpty(message = "attribute is required") String attribute_name_ascii) {
}
