package com.example.demo.category.dto;

import java.util.List;

import com.example.demo.category_attribute.dto.CategoryAttributeDto;

import lombok.NonNull;

public record ProductCategoryDto(

        Long id,

        @NonNull String category_ascii,

        @NonNull String category_name,

        String attribute_order,

        List<CategoryAttributeDto> attributes

) {
}
