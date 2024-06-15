package com.example.demo.product_slider.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.product_slider.dto.ProductSliderDto;
import com.example.demo.product_slider.entity.ProductSlider;

@Component
public class ProductSliderDtoToProductSlider implements Converter<ProductSliderDto, ProductSlider> {

    @Override
    public ProductSlider convert(ProductSliderDto source) {
        ProductSlider productSlider = new ProductSlider();

        // productSlider.setProduct_ascii(source.product_ascii());
        productSlider.setSlider_id(source.slider_id());
        productSlider.setColor_id(source.color_id());

        return productSlider;
    }

}
