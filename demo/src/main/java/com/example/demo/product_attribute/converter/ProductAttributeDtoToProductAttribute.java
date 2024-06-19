package com.example.demo.product_attribute.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.product_attribute.dto.ProductAttributeDto;
import com.example.demo.product_attribute.entity.ProductAttribute;

@Component
public class ProductAttributeDtoToProductAttribute implements Converter<ProductAttributeDto, ProductAttribute> {

    @Override
    public ProductAttribute convert(ProductAttributeDto source) {
        ProductAttribute data = new ProductAttribute();

        data.setCategory_attribute_id(source.category_attribute_id());
        data.setProduct_id(source.product_id());
        data.setValue(source.value());

        return data;
    }

}
