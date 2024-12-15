package com.example.demo.product_attribute.entity;

import com.example.demo.category_attribute.entity.CategoryAttribute;
import com.example.demo.product.entity.Product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;


@Data
@Entity(name = "product_attributes")
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "product_unique", columnNames = { "product_id", "category_attribute_id" })
})
public class ProductAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 
    @Column(nullable = false)
    private Long category_attribute_id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_attribute_id", insertable = false, updatable = false)
    private CategoryAttribute categoryAttribute;

    //
    @Column(nullable = false)
    private Long product_id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    @Column(nullable = false, columnDefinition="TEXT")
    private String value;
}
