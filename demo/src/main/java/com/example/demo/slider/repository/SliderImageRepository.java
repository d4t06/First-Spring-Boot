package com.example.demo.slider.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.slider.entity.SliderImage;

public interface SliderImageRepository extends JpaRepository<SliderImage, Long> {

}
