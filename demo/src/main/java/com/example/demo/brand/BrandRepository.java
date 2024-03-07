package com.example.demo.brand;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.brand.entity.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

}
