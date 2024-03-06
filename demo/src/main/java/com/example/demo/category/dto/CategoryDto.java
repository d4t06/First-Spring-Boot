package com.example.demo.category.dto;

import lombok.NonNull;

public record CategoryDto(
        @NonNull 
        String category_ascii,
        
        @NonNull 
        String category_name) {
}
