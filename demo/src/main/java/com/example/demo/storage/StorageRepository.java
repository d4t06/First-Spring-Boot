package com.example.demo.storage;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.storage.entity.Storage;

public interface StorageRepository extends JpaRepository<Storage, Long> {

    List<Storage> findByProductAscii(String productAscii);
}
