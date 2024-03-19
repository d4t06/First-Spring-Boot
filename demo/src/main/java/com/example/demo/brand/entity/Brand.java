package com.example.demo.brand.entity;

import com.example.demo.category.entity.Category;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "brands")

public class Brand {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(nullable = false)
   private String brand_name;

   @Column(nullable = false)
   private String brand_ascii;

   @Column(nullable = false)
   private Long category_id;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(
      name = "category_id",
      insertable = false,  
      updatable = false
   )
   private Category category;
}
