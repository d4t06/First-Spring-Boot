package com.example.demo.storage.entity;

import java.util.List;

import com.example.demo.combine.entity.Combine;
import com.example.demo.default_storage.entity.DefaultStorage;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Data
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "product_unique", columnNames = { "product_id", "storage_name_ascii" })
})
public class Storage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String storage_name_ascii;

    @Column(nullable = false)
    private String storage_name;

    //
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "storage")
    private List<Combine> combines;

    //
    @Column(nullable = false, name = "product_id")
    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    //
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "storage")
    private DefaultStorageCombine defaultStorageCombine;

    //
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "storage")
    private DefaultStorage defaultStorage;
}
