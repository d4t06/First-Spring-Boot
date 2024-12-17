package com.example.demo.slider.entity;

import com.example.demo.image.entity.Image;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity(name = "slider_images")
@Data
public class SliderImage {
   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE)
   private Long id;

   private String link_to;

   // **************
   @Column(nullable = false)
   private Long image_id;

   @OneToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "image_id", insertable = false, updatable = false)
   private Image image;

   // **************
   @Column(nullable = false)
   private Long slider_id;

   @ManyToOne()
   @JoinColumn(name = "slider_id", insertable = false, updatable = false)
   private Slider slider;
}
