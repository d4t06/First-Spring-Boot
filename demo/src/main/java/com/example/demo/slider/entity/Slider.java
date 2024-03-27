package com.example.demo.slider.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity (name = "sliders")
@Data
public class Slider {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private String name;

   @OneToMany(mappedBy = "slider", cascade = CascadeType.ALL, orphanRemoval = true)
   private List<SliderImage> sliderImages = new ArrayList<SliderImage>();
}
