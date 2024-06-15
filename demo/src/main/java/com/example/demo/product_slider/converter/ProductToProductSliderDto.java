package com.example.demo.product_slider.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.product_slider.dto.ProductSliderDto;
import com.example.demo.product_slider.entity.ProductSlider;
import com.example.demo.slider.converter.SliderToSliderDto;

@Component
public class ProductToProductSliderDto implements Converter<ProductSlider, ProductSliderDto> {

    private final SliderToSliderDto sliderToSliderDto;

    public ProductToProductSliderDto(SliderToSliderDto sliderToSliderDto) {
        this.sliderToSliderDto = sliderToSliderDto;
    }

    @Override
    public ProductSliderDto convert(ProductSlider source) {
        ProductSliderDto productSliderDto = new ProductSliderDto(
                source.getId(),
                source.getColor_id(),
                source.getSlider_id(),
                source.getSlider() != null
                        ? this.sliderToSliderDto.convert(source.getSlider())
                        : null);

        return productSliderDto;
    }

}
