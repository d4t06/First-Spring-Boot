package com.example.demo.description.dto;

import jakarta.validation.constraints.NotEmpty;

public record ProductDescDto(
                @NotEmpty(message = "product_ascii is required") Long product_id,
                @NotEmpty(message = "content is required") String content) {

}
