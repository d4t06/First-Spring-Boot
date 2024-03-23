package com.example.demo.slider.entity;

import com.example.demo.image.entity.Image;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class SliderImage {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   // **************
   @Column(nullable = false)
   private Long image_id;

   @OneToOne()
   @JoinColumn(name = "image_id", insertable = false, updatable = false)
   private Image image;

   // **************
   @Column(nullable = false)
   private Long slider_id;

   @ManyToOne()
   @JoinColumn(name = "slider_id", insertable = false, updatable = false)
   private Slider slider;
}
