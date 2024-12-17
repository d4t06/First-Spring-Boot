package com.example.demo.cart_item.entity;

import com.example.demo.product.entity.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity(name = "Cart_Items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, name = "color_id")
    private Long colorId;

    @Column(nullable = false, name = "storage_id")
    private Long storageId;

    @Column(nullable = false)
    private int amount;

    // >>>>>>>>>>>>
    @Column(nullable = false)
    private Long product_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;
}
