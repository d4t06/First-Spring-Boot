package com.example.demo.product.dto;

import java.util.List;

import lombok.Data;

@Data
public class ProductResponse<T> {
      List<T> products;
      Integer page;
      Long count;
      String category_id;
      Integer size;
      List<String> brand_id;
      String column;
      String type;
      List<String> price;
      Boolean is_last;
      String q;
}
