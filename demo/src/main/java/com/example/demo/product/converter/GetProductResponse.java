package com.example.demo.product.converter;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Component;

import com.example.demo.product.ProductFilter;
import com.example.demo.product.dto.ProductResponse;
import com.example.demo.product.entity.Product;

@Component
public class GetProductResponse<T> {

   public ProductResponse<T> create(Page<Product> productPage, Pageable pageable,
         ProductFilter filter, List<T> payload) {

      ProductResponse<T> res = new ProductResponse<T>();

      res.setProducts(payload);
      res.setPage(pageable.getPageNumber());
      res.setSize(pageable.getPageSize());
      res.setCount(productPage.getTotalElements());
      res.setBrand_id(filter.brand_id());
      res.setCategory_id(filter.category_id());
      res.setIs_last(productPage.isLast());
      res.setPrice(filter.price());

      if (pageable.getSort().isSorted()) {
         Order order = pageable.getSort().toList().get(0);

         res.setColumn(order.getProperty());
         res.setType(order.getDirection().name());
      }

      return res;

   }

}
