package com.example.demo.slider.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.slider.entity.SliderImage;

@Repository
public interface SliderImageRepository extends JpaRepository<SliderImage, Long> {

}
