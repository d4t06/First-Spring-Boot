package com.example.demo.description.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.description.dto.ProductDescDto;
import com.example.demo.description.entity.ProductDesc;

@Component
public class ProductDescDtoToProductDesc implements Converter<ProductDescDto, ProductDesc> {

    @Override
    public ProductDesc convert(ProductDescDto source) {
        ProductDesc desc = new ProductDesc();

        desc.setProductAscii(source.product_ascii());
        desc.setContent(source.content());

        return desc;
    }

}
