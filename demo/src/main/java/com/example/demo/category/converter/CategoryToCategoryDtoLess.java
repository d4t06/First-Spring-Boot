
package com.example.demo.category.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.brand.converter.BrandToBrandDto;
import com.example.demo.brand.dto.BrandDto;
import com.example.demo.brand.entity.Brand;
import com.example.demo.category.dto.CategoryDtoLess;
import com.example.demo.category.entity.Category;
import com.example.demo.category_attribute.converter.CategoryAttToCategoryAttDto;

@Component
public class CategoryToCategoryDtoLess implements Converter<Category, CategoryDtoLess> {

   private final BrandToBrandDto brandToBrandDto;
   private final CategoryAttToCategoryAttDto categoryAttToCategoryAttDto;

   public CategoryToCategoryDtoLess(
         BrandToBrandDto brandToBrandDto,
         CategoryAttToCategoryAttDto categoryAttToCategoryAttDto) {
      this.brandToBrandDto = brandToBrandDto;
      this.categoryAttToCategoryAttDto = categoryAttToCategoryAttDto;
   }

   List<BrandDto> getBrandsDto(List<Brand> brands) {
      ArrayList<BrandDto> brandsDto = new ArrayList<>();
      for (Brand brand : brands) {
         BrandDto brandDto = this.brandToBrandDto.convert(brand);
         brandsDto.add(brandDto);
      }

      return brandsDto;
   }

   @Override
   public CategoryDtoLess convert(Category source) {

      CategoryDtoLess data = new CategoryDtoLess(
            source.getId(),
            source.getCategory_name_ascii(),
            source.getBrands().isEmpty()
                  ? new ArrayList<BrandDto>()
                  : getBrandsDto(source.getBrands()),
            source.getCategoryAttributes().isEmpty()
                  ? new ArrayList<>()
                  : source.getCategoryAttributes().stream()
                        .map(catAttr -> this.categoryAttToCategoryAttDto.convert(catAttr)).toList());

      return data;
   }
}