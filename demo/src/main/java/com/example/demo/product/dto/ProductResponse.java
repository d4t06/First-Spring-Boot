package com.example.demo.product.dto;

import java.util.List;

public record ProductResponse(
      List<ProductDTO> products,
      Integer page,
      Long count,
      Integer categoryID,
      List<String> BrandID,
      List<String> sort,
      boolean last
) {}
                                                     