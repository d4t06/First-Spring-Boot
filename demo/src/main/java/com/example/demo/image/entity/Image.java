package com.example.demo.image.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.example.demo.slider.entity.SliderImage;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "images")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;

   private String image_name;

   private String image_url;

   private String publicID;

   private int size;

   // if image remove then remove slider image too
   @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   private SliderImage sliderImage;

   @CreationTimestamp
   private LocalDateTime created_at;
}
