package com.example.demo.category_attribute.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.category_attribute.dto.CategoryAttributeDto;
import com.example.demo.category_attribute.entity.CategoryAttribute;

@Component
public class CategoryAttToCategoryAttDto implements Converter<CategoryAttribute, CategoryAttributeDto> {

    @Override
    public CategoryAttributeDto convert(CategoryAttribute source) {
        CategoryAttributeDto data = new CategoryAttributeDto(
                source.getId(),
                source.getCategory_id(),
                source.getAttribute_name(),
                source.getAttribute_name_ascii());

        return data;
    }

}
