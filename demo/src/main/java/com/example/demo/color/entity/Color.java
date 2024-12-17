package com.example.demo.color.entity;

import java.util.List;

import com.example.demo.combine.entity.Combine;
import com.example.demo.product.entity.Product;
import com.example.demo.product_slider.entity.ProductSlider;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Data
@Entity(name = "product_colors")
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "product_unique", columnNames = { "product_id", "color_name_ascii" })
})
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String color_name_ascii;

    @Column(nullable = false)
    private String color_name;

    //
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "color")
    private ProductSlider productSlider;

    //
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "color")
    private List<Combine> combines;

    //
    @Column(nullable = false, name = "product_id")
    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;
}
