package com.example.demo.brand.entity;

import java.util.List;
import com.example.demo.category.entity.Category;
import com.example.demo.product.entity.Product;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "brands")
@Data
@NoArgsConstructor
@AllArgsConstructor

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

   @OneToMany(cascade = CascadeType.ALL, mappedBy = "brand")
   private List<Product> products;

}
