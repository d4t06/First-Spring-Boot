package com.example.demo.category.entity;

import java.util.ArrayList;
import java.util.List;
import com.example.demo.brand.entity.Brand;
import com.example.demo.category_attribute.entity.CategoryAttribute;
import com.example.demo.price_range.entity.PriceRange;
import com.example.demo.product.entity.Product;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity(name = "categories")

public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer is_show;

    @Column(nullable = false)
    private String category_name;

    @Column(nullable = false, unique = true)
    private String category_name_ascii;

    @Column(nullable = false)
    private String attribute_order;

    // ***************
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Brand> brands = new ArrayList<Brand>();

    // ***************
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    // mapped is column in product table
    // cascade all allow cascade for all operations
    // fetch lazy,
    private List<Product> products = new ArrayList<Product>();

    // ***************
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PriceRange> priceRanges = new ArrayList<PriceRange>();

    // ***************
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CategoryAttribute> categoryAttributes = new ArrayList<CategoryAttribute>();

    // ***************
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "category")
    private CategorySlider categorySlider;
}
