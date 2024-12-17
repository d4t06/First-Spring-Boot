package com.example.demo.category_attribute.entity;

import com.example.demo.category.entity.Category;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Data()
@Entity(name = "category_attributes")
@Table(uniqueConstraints = {
      @UniqueConstraint(name = "attribute_unique", columnNames = { "category_id", "attribute_name_ascii" })
})
public class CategoryAttribute {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(nullable = false)
   private String attribute_name;

   @Column(nullable = false)
   private String attribute_name_ascii;

   @Column(nullable = false)
   private Long category_id;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "category_id", insertable = false, updatable = false)
   private Category category;
}
