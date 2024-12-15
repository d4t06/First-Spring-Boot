package com.example.demo.category;

import org.springframework.data.jpa.domain.Specification;

import com.example.demo.category.entity.Category;

public class CategorySpecs {

   public static Specification<Category> hasNameAscii(String nameAscii) {
      return (root, query, criteriaBuilder) -> criteriaBuilder
            .equal(root.get("category_name_ascii"), nameAscii);
   }

}
