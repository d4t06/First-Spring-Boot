package com.example.demo.image.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

   private String name;

   private String image_url;

   private String public_id;

   private int size;

   @CreationTimestamp
   private LocalDateTime created_at;
}
