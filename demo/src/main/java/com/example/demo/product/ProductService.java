package com.example.demo.product;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.product.converter.ProductDtoToProduct;
import com.example.demo.product.dto.ProductDTO;
import com.example.demo.product.entity.Product;
import com.example.demo.system.exception.ObjectNotFoundException;

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
        List<Product> products = this.productRepository.findAll();

        if (products.isEmpty())
            throw new ObjectNotFoundException("Product not found");

        return products;
    }

    public Product findOne(String product_ascii) {
        List<Product> products = this.productRepository.findByProductAscii(product_ascii);

        if (products.size() != 1)
            throw new ObjectNotFoundException("Product not found");
        return products.get(0);

    }

    public Product create(ProductDTO createProductDTO) {
        Product product = this.productDtoToProduct.convert(createProductDTO);
        return this.productRepository.save(product);
    }

    public Product update(Long id, ProductDTO updateDto) {
        return this.productRepository.findById(id)
                .map(oldProduct -> {
                    oldProduct.setBrandId(updateDto.brand_id());
                    oldProduct.setCategoryId(updateDto.category_id());
                    oldProduct.setProductAscii(updateDto.product_ascii());
                    oldProduct.setProduct_name(updateDto.product_name());
                    oldProduct.setImage_url(updateDto.image_url());
                    oldProduct.setPrice(updateDto.price());

                    Product product = this.productRepository.save(oldProduct);
                    return product;
                })
                .orElseThrow(() -> new ObjectNotFoundException("Product not found"));

    }

    public void delete(Long id) {
        this.productRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Product not found"));

        this.productRepository.deleteById(id);
    }

}
