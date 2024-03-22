package com.example.demo.product.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ProductResponse {
      List<ProductDTO> products;
      Integer page;
      Long count;
      Integer categoryID;
      Integer pageSize;
      List<String> brandID;
      String column;
      String type;
      List<String> price;
      Boolean isLast;
}
