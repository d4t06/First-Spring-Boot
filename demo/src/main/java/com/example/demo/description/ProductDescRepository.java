package com.example.demo.description;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.description.entity.ProductDesc;

public interface ProductDescRepository extends JpaRepository<ProductDesc, String> {

}
