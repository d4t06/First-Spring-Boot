package com.example.demo.product_attribute.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.product_attribute.dto.ProductAttributeDto;
import com.example.demo.product_attribute.entity.ProductAttribute;


@Component
public class ProductAttributeToProductAttributeDto implements Converter<ProductAttribute, ProductAttributeDto> {

    @Override
    public ProductAttributeDto convert(ProductAttribute source) {

        ProductAttributeDto data = new ProductAttributeDto(
                source.getId(),
                source.getProduct_id(),
                source.getCategory_attribute_id(),
                source.getValue());

        return data;
    }

}
