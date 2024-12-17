package com.example.demo.product_slider;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.product_slider.entity.ProductSlider;

public interface ProductSliderRepository extends JpaRepository<ProductSlider, Long> {
    
}
