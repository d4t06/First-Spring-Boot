package com.example.demo.product_slider.entity;

import com.example.demo.color.entity.Color;
import com.example.demo.slider.entity.Slider;

import jakarta.persistence.CascadeType;
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
@Entity(name = "product_sliders")
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "product_unique", columnNames = { "product_ascii", "color_id" })
})
public class ProductSlider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @Column(nullable = false, unique = true)
    // private String product_ascii;

    // @OneToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "product_ascii", insertable = false, updatable = false)
    // private Product product;

    //
    @Column(nullable = false, unique = true)
    private Long color_id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "color_id", insertable = false, updatable = false)
    private Color color;

    @Column(nullable = false)
    private Long slider_id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "slider_id", insertable = false, updatable = false)
    private Slider slider;
}
