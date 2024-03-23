package com.example.demo.slider.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.slider.entity.Slider;

public interface SliderRepository extends JpaRepository<Slider, Long> {
   
}
