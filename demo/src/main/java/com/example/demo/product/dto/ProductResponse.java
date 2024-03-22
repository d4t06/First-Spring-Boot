package com.example.demo.product.dto;

import java.util.List;

public record ProductResponse(
      List<ProductDTO> products,
      Integer page,
      Long count,
      Integer categoryID,
      Integer pageSize,
      List<String> brandID,
      List<String> sort,
      boolean isLast
) {}
                                                     