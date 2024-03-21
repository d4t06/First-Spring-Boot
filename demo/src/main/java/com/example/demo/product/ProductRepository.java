package com.example.demo.product;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.demo.product.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByProductAscii(String productAscii);

    List<Product> findByBrandId(Long brandId);

    List<Product> findByCategoryId(Long categoryId);

    @Query(value = "select * from products where" +
            " product_name like %:keyword%", nativeQuery = true)
    Page<Product> findByKeyword(Pageable pageable, String keyword);

    @Query(value = "SELECT * FROM products  WHERE" +
            " category_id =:categoryID and brand_id IN (:brandID)", nativeQuery = true)
    Page<Product> findAllWithParams(
            Pageable pageable,
            @Param("categoryID") int categoryID,
            @Param("brandID") List<String> brandID);

}
