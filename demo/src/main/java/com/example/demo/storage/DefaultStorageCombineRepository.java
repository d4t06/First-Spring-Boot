package com.example.demo.storage;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.storage.entity.DefaultStorageCombine;

@Repository
public interface DefaultStorageCombineRepository extends JpaRepository<DefaultStorageCombine, Long> {

    Optional<DefaultStorageCombine> findByStorageId(Long storageId);
}
