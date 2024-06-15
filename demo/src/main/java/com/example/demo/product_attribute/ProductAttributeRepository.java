package com.example.demo.product_attribute;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.product_attribute.entity.ProductAttribute;

public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Long> {
    
}
