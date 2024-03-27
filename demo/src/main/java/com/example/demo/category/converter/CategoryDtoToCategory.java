package com.example.demo.category.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.category.dto.CategoryDto;
import com.example.demo.category.entity.Category;

@Component
public class CategoryDtoToCategory implements Converter<CategoryDto, Category> {

    @Override
    public Category convert(CategoryDto source) {
        Category category = new Category();
        category.setCategory_ascii(source.category_ascii());
        category.setCategory_name(source.category_name());
        category.setIs_show(1);
        return category;
    }

}
