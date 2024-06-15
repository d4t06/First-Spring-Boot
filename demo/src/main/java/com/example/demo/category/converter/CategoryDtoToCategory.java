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


        System.out.println("ccheck sour" + source.attribute_order());

        category.setCategory_ascii(source.category_ascii());
        category.setCategory_name(source.category_name());
        category.setAttribute_order(source.attribute_order());
        category.setIs_show(source.is_show() == null ? 1 : source.is_show());

        return category;
    }

}
