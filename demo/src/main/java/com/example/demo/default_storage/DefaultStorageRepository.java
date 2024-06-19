package com.example.demo.default_storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.default_storage.entity.DefaultStorage;

@Repository
public interface DefaultStorageRepository extends JpaRepository<DefaultStorage, Long> {

}
