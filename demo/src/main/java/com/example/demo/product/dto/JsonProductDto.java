package com.example.demo.product.dto;

import java.util.List;

public record JsonProductDto(
      String name,
      Long brand_id,
      Long category_id,
      Integer price,
      String image,
      String description,
      List<JsonAttributeDto> attributes,
      List<String> sliders,
      List<String> colors,
      List<String> storages) {

}
