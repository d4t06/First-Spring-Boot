package com.example.demo.product.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.product.dto.ProductDTO;
import com.example.demo.product.entity.Product;

@Component
public class ProductDtoToProduct implements Converter<ProductDTO, Product> {

    @Override
    public Product convert(ProductDTO source) {
        Product product = new Product();

        product.setProduct_name(source.product_name());
        product.setProductAscii(source.product_ascii());
        product.setBrandId(source.brand_id());
        product.setCategoryId(source.category_id());
        product.setImage_url(source.image_url());
        product.setPrice(source.price());
        product.setInstallment(source.installment() || false);

        return product;
    }

}
