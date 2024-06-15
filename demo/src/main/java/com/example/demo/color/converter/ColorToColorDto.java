package com.example.demo.color.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.color.dto.ColorDto;
import com.example.demo.color.entity.Color;
import com.example.demo.product_slider.converter.ProductToProductSliderDto;

@Component
public class ColorToColorDto implements Converter<Color, ColorDto> {
    private final ProductToProductSliderDto productToProductSliderDto;

    public ColorToColorDto(ProductToProductSliderDto productToProductSliderDto) {
        this.productToProductSliderDto = productToProductSliderDto;
    }

    @Override
    public ColorDto convert(Color source) {

        ColorDto colorDto = new ColorDto(
                source.getId(),
                source.getColor(),
                source.getColor_ascii(),
                source.getProductAscii(),
                this.productToProductSliderDto.convert(source.getProductSlider()));

        return colorDto;
    }

}
