package com.example.demo.product;

import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import com.example.demo.product.entity.Product;

public class ProductSpecs {

    public static Specification<Product> hasCategoryID(String providedCategoryID) {
        // return new Specification<Product>() {
        // @Override
        // public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query,
        // CriteriaBuilder criteriaBuilder) {
        // return criteriaBuilder.equal(root.get("category_id"), providedCategoryID);
        // }
        // };
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("category_id"), providedCategoryID);
    }

    public static Specification<Product> hasBrandIDs(List<String> brandIDs) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get("brand_id")).value(brandIDs);
    }

}
