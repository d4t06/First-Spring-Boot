package com.example.demo.product;

import java.util.List;

public record ProductFilter(
      String category_id,
      List<String> brand_id,
      List<String> price) {
}