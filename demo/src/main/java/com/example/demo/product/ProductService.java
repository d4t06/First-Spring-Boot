package com.example.demo.product;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.category.exception.CategoryNotFoundException;
import com.example.demo.product.converter.ProductDtoToProduct;
import com.example.demo.product.dto.ProductDTO;
import com.example.demo.product.entity.Product;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductDtoToProduct productDtoToProduct;

    public ProductService(
            ProductRepository productRepository,
            ProductDtoToProduct productDtoToProduct) {
        this.productRepository = productRepository;
        this.productDtoToProduct = productDtoToProduct;
    }

    public List<Product> findAll() {
        return this.productRepository.findAll();
    }

    public Product findOne(String product_ascii) {
        List<Product> products = this.productRepository.findByProductAscii(product_ascii);

        if (products.size() != 1)
            throw new CategoryNotFoundException("akdsjfkljsad");

        return products.get(0);

    }

    public Product create(ProductDTO createProductDTO) {
        Product product = this.productDtoToProduct.convert(createProductDTO);
        return this.productRepository.save(product);
    }

    public void update(String product_ascii) {
        Product oldProduct = this.productRepository.findByProductAscii(product_ascii).get(0);



    }

}
