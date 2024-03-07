package com.example.demo.brand.converter;

import org.springframework.core.convert.converter.Converter;

import com.example.demo.brand.dto.BrandDto;
import com.example.demo.brand.entity.Brand;

public class BrandDtoToBrand implements Converter<BrandDto, Brand> {

   @Override
   public Brand convert(BrandDto source) {
      Brand brand = new Brand();

      brand.setBrand_ascii(source.brand_ascii());
      brand.setBrand_name(source.brand_name());
      brand.setCategoryAscii(source.category_ascii());

      return brand;
   }
   
}
