package com.example.demo.product.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.demo.brand.entity.Brand;
import com.example.demo.category.entity.Category;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products", uniqueConstraints = {
        @UniqueConstraint(name = "product_unique", columnNames = "product_ascii")
})
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_sequence")
    @SequenceGenerator(name = "product_sequence", sequenceName = "product_sequence", allocationSize = 100)
    private Long id;

    @Column(nullable = false)
    private String product_name;

    @Column(nullable = false, name = "product_ascii")
    private String productAscii;

    private String image_url;
    @Column(nullable = false)
    private int price;
    private Boolean installment;

    // *********
    @Column(nullable = false, name = "category_id")
    private Long categoryId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private Category category;

    // *********
    @Column(nullable = false, name = "brand_id")
    private Long brandId;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "brand_id", insertable = false, updatable = false)
    private Brand brand;

    @CreationTimestamp
    private LocalDateTime created_at;
    @UpdateTimestamp
    private LocalDateTime updated_at;
}