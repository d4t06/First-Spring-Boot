package com.example.demo.default_storage.entity;

import com.example.demo.product.entity.Product;
import com.example.demo.storage.entity.Storage;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity(name = "default_product_storages")
public class DefaultStorage {
    @Id
    @Column(name = "product_ascii")
    private String productAscii;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_ascii", insertable = false, updatable = false)
    private Product product;

    @Column()
    private Long storage_id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storage_id", insertable = false, updatable = false)
    private Storage storage;
}
