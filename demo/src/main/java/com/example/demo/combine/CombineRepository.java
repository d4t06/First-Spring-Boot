package com.example.demo.combine;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.combine.entity.Combine;

public interface CombineRepository extends JpaRepository<Combine, Long> {
    List<Combine> findByStorageId(Long storageId);

    Optional<Combine> findByStorageIdAndColorId(Long storageId, Long colorId);
}
