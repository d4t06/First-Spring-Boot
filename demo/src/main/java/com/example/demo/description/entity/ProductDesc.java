package com.example.demo.description.entity;

import com.example.demo.product.entity.Product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data()
@Entity(name = "product_descriptions")
public class ProductDesc {

    @Id
    @Column(name = "product_ascii")
    private String productAscii;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_ascii", insertable = false, updatable = false)
    private Product product;

    @Column(nullable = false, columnDefinition="TEXT")
    private String content;

}
