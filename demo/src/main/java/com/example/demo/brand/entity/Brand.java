package com.example.demo.brand.entity;

import com.example.demo.category.entity.Category;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

   @Column(nullable = false, unique = true)
   private String brand_ascii;

   @Column(nullable = false, name = "brand_ascii")
   private String categoryAscii;

   @ManyToOne()
   @JoinColumn(
      name = "category_ascii", 
      insertable = false,
      updatable = false,
      referencedColumnName = "category_ascii"
   )
   private Category category;
}
