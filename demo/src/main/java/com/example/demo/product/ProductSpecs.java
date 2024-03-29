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
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .equal(root.get("categoryId"), providedCategoryID);
    }

    public static Specification<Product> containName(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .like(criteriaBuilder.lower(root.get("product_name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Product> hasBrandIDs(List<String> brandIDs) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .in(root.get("brandId")).value(brandIDs);
    }

    public static Specification<Product> betweenPrice(Integer fromPrice, Integer toPrice) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .between(root.<Integer>get("price"), fromPrice, toPrice);
    }
}
