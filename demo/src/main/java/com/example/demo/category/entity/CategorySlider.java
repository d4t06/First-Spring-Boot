package com.example.demo.category.entity;

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
import lombok.Data;

@Entity(name = "category_sliders")
@Data
public class CategorySlider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long category_id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private Category category;

    @Column(nullable = false)
    private Long slider_id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "slider_id", insertable = false, updatable = false)
    private Slider slider;
}
