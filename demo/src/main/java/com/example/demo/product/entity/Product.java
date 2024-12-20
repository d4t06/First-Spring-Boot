package com.example.demo.product.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.demo.brand.entity.Brand;
import com.example.demo.cart_item.entity.CartItem;
import com.example.demo.category.entity.Category;
import com.example.demo.color.entity.Color;
import com.example.demo.product_attribute.entity.ProductAttribute;
import com.example.demo.storage.entity.Storage;
import com.example.demo.combine.entity.Combine;
import com.example.demo.default_storage.entity.DefaultStorage;
import com.example.demo.description.entity.ProductDesc;

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
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Product implements Serializable {
   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE)
   // @SequenceGenerator(name = "seqGen", sequenceName = "seq", initialValue = 1)
   private Long id;

   @Column(nullable = false)
   private String product_name;

   @Column(nullable = false, unique = true)
   private String product_name_ascii;

   private String image_url;
   @Column(nullable = false)
   private int price;
   private Boolean installment;

   // *********
   @Column(nullable = false, name = "category_id")
   private Long categoryId;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "category_id", insertable = false, updatable = false)
   private Category category;

   // *********
   @Column(nullable = false, name = "brand_id")
   private Long brandId;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "brand_id", insertable = false, updatable = false)
   private Brand brand;

   @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
   private List<Storage> storages = new ArrayList<Storage>();

   @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
   private List<Color> colors = new ArrayList<Color>();

   @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
   private List<Combine> Combines = new ArrayList<Combine>();

   @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
   private List<ProductAttribute> attributes = new ArrayList<ProductAttribute>();
   // need to define mappedBy to get data otherwise empty array

   @OneToOne(cascade = CascadeType.ALL, mappedBy = "product")
   private ProductDesc productDescription;

   @OneToOne(cascade = CascadeType.ALL, mappedBy = "product")
   private DefaultStorage defaultStorage;

   @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
   private List<CartItem> carts;

   @CreationTimestamp
   private LocalDateTime created_at;
   @UpdateTimestamp
   private LocalDateTime updated_at;
}