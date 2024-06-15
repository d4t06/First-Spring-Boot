package com.example.demo.description.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.description.dto.ProductDescDto;
import com.example.demo.description.entity.ProductDesc;


@Component
public class ProductDescToProductDescDto implements Converter<ProductDesc, ProductDescDto> {

    @Override
    public ProductDescDto convert(ProductDesc source) {
        ProductDescDto detailDto = new ProductDescDto( source.getProductAscii(), source.getContent());

        return detailDto;
    }

}
