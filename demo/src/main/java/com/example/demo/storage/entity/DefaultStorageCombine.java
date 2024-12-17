package com.example.demo.storage.entity;

import com.example.demo.combine.entity.Combine;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity(name = "default_storage_combines")
public class DefaultStorageCombine {

    //
    @Id
    @Column(name = "storage_id")
    private Long storageId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storage_id", insertable = false, updatable = false)
    private Storage storage;

    //
    @Column()
    private Long combine_id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "combine_id", insertable = false, updatable = false)
    private Combine combine;
}
