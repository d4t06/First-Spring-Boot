package com.example.demo.category.dto;

import java.util.List;

import com.example.demo.brand.dto.BrandDto;

import lombok.NonNull;

public record CategoryDto(
        @NonNull 
        String category_ascii,
        
        @NonNull 
        String category_name,

        Long id,

        List<BrandDto> brands
        ) {
}
