package com.example.demo.category.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.category.dto.CategorySliderDto;
import com.example.demo.category.entity.CategorySlider;
import com.example.demo.slider.converter.SliderToSliderDto;

@Component
public class CategorySliderToCategorySliderDto implements Converter<CategorySlider, CategorySliderDto> {

    private final SliderToSliderDto sliderToSliderDto;

    public CategorySliderToCategorySliderDto(SliderToSliderDto sliderToSliderDto) {
        this.sliderToSliderDto = sliderToSliderDto;
    }

    @Override
    public CategorySliderDto convert(CategorySlider source) {

        CategorySliderDto categorySliderDto = new CategorySliderDto(
                source.getId(),
                source.getCategory_id(),
                source.getSlider_id(),
                source.getSlider() != null
                        ? this.sliderToSliderDto.convert(source.getSlider())
                        : null);

        return categorySliderDto;
    }

}
