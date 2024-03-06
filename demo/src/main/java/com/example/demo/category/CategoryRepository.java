package com.example.demo.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.category.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
