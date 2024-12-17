package com.example.demo.category.dto;

import java.util.List;

import com.example.demo.brand.dto.BrandDto;
import com.example.demo.category_attribute.dto.CategoryAttributeDto;

import lombok.NonNull;

public record CategoryDtoLess(
      Long id,

      @NonNull String category_name_ascii,

      List<BrandDto> brands,

      List<CategoryAttributeDto> attributes

) {
}
