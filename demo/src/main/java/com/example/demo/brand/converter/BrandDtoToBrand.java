package com.example.demo.brand.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.brand.dto.BrandDto;
import com.example.demo.brand.entity.Brand;

@Component
public class BrandDtoToBrand implements Converter<BrandDto, Brand> {

   @Override
   public Brand convert(BrandDto source) {
      Brand brand = new Brand();

      brand.setBrand_name(source.brand_name());
      brand.setBrand_name_ascii(source.brand_name_ascii());
      brand.setCategory_id(source.category_id());

      return brand;
   }

}
