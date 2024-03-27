package com.example.demo.category.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.category.dto.CategorySliderDto;
import com.example.demo.category.entity.CategorySlider;

@Component
public class CategorySliderDtoToCategorySlider implements Converter<CategorySliderDto, CategorySlider> {

    @Override
    public CategorySlider convert(CategorySliderDto source) {

        CategorySlider categorySlider = new CategorySlider();

        categorySlider.setCategory_id(source.category_id());
        categorySlider.setSlider_id(source.slider_id());

        return categorySlider;
    }

}
