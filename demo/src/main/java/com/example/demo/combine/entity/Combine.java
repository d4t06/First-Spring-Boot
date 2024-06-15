package com.example.demo.combine.entity;

import com.example.demo.storage.entity.Storage;
import com.example.demo.color.entity.Color;
import com.example.demo.product.entity.Product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Data
@Entity(name = "product_combines")
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "product_unique", columnNames = { "product_ascii", "storage_ascii", "color_ascii" })
})
public class Combine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int quantity;

    //
    @Column(nullable = false)
    private Long storage_id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storage_id", insertable = false, updatable = false)
    private Storage storage;

    //
    @Column(nullable = false)
    private Long color_id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "color_id", insertable = false, updatable = false)
    private Color color;

    //
    @Column(nullable = false, name = "product_ascii")
    private String productAscii;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_ascii", insertable = false, updatable = false)
    private Product product;
}
