package com.example.demo.category.converter;

import java.util.ArrayList;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.category.dto.ProductCategoryDto;
import com.example.demo.category.entity.Category;
import com.example.demo.category_attribute.converter.CategoryAttToCategoryAttDto;

@Component
public class CategoryToProductCategoryDto implements Converter<Category, ProductCategoryDto> {

    private final CategoryAttToCategoryAttDto attrToAttrDto;

    public CategoryToProductCategoryDto(CategoryAttToCategoryAttDto attrToAttrDto) {
        this.attrToAttrDto = attrToAttrDto;
    }

    @Override
    public ProductCategoryDto convert(Category source) {

        ProductCategoryDto categoryDto = new ProductCategoryDto(
                source.getId(),
                source.getCategory_name_ascii(),
                source.getCategory_name(),
                source.getAttribute_order(),
                source.getCategoryAttributes().isEmpty()
                        ? new ArrayList<>()
                        : source.getCategoryAttributes().stream().map(attr -> this.attrToAttrDto.convert(attr))
                                .toList());

        return categoryDto;
    }

}
