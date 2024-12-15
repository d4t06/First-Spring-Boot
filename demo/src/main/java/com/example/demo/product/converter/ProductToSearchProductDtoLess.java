package com.example.demo.product.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.product.dto.SearchProductDto;
import com.example.demo.product.entity.Product;

@Component
public class ProductToSearchProductDtoLess implements Converter<Product, SearchProductDto> {

   @Override
   public SearchProductDto convert(Product source) {
      SearchProductDto dto = new SearchProductDto(
            source.getId(),
            source.getProduct_name(),
            source.getProduct_name_ascii(),
            source.getImage_url(),
            source.getInstallment() == null ? false : true,
            source.getCategoryId(),
            source.getBrandId(),
            null);

      return dto;
   }

}
