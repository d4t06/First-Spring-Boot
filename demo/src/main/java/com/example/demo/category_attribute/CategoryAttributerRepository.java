package com.example.demo.category_attribute;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.category_attribute.entity.CategoryAttribute;

@Repository
public interface CategoryAttributerRepository extends JpaRepository<CategoryAttribute, Long> {

}
