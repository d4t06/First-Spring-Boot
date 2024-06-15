package com.example.demo.category_attribute.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.category_attribute.dto.CategoryAttributeDto;
import com.example.demo.category_attribute.entity.CategoryAttribute;

@Component
public class CategoryAttDToCategoryAtt implements Converter<CategoryAttributeDto, CategoryAttribute> {

    @Override
    public CategoryAttribute convert(CategoryAttributeDto source) {

        CategoryAttribute data = new CategoryAttribute();

        data.setCategory_id(source.category_id());
        data.setAttribute(source.attribute());
        data.setAttribute_ascii(source.attribute_ascii());

        return data;
    }

}
