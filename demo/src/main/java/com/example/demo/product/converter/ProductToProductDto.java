package com.example.demo.product.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.product.dto.ProductDTO;
import com.example.demo.product.entity.Product;


@Component
public class ProductToProductDto implements Converter<Product, ProductDTO> {

    @Override
    public ProductDTO convert(Product source) {
        ProductDTO productDto = new ProductDTO(
                source.getId(),
                source.getProduct_name(),
                source.getProductAscii(),
                source.getCategoryId(),
                source.getBrandId(),
                source.getImage_url(),
                source.getPrice(),
                source.getInstallment()
        );

        return productDto;
    }

}
