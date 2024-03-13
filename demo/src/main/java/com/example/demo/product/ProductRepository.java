package com.example.demo.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.product.entity.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByProductAscii(String productAscii);
    List<Product> findByBrandId(Long brandId);
    List<Product> findByCategoryId(Long categoryId);
}