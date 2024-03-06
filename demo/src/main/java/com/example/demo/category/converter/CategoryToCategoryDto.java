
package com.example.demo.category.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.category.dto.CategoryDto;
import com.example.demo.category.entity.Category;


@Component
class CategoryToCategoryDto implements Converter<Category, CategoryDto> {

    @Override
    public CategoryDto convert(Category source) {
        CategoryDto dto = new CategoryDto(source.getCategory_ascii(), source.getCategory_name());
        return dto;
    }

    
}