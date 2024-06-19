package com.example.demo.category_attribute.entity;

import com.example.demo.category.entity.Category;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data()
@Entity(name = "category_attributes")
public class CategoryAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String attribute_name;

    @Column(nullable = false, unique = true)
    private String attribute_name_ascii;

    //
    @Column(nullable = false)
    private Long category_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private Category category;
}
