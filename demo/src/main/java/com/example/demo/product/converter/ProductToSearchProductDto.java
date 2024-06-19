package com.example.demo.product.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.default_storage.converter.DStorageToDStorageDetailDto;
import com.example.demo.product.dto.SearchProductDto;
import com.example.demo.product.entity.Product;

@Component
public class ProductToSearchProductDto implements Converter<Product, SearchProductDto> {

    private final DStorageToDStorageDetailDto dStorageDetailDto;

    public ProductToSearchProductDto(
            DStorageToDStorageDetailDto dStorageDetailDto) {
        this.dStorageDetailDto = dStorageDetailDto;
    }

    @Override
    public SearchProductDto convert(Product source) {
        SearchProductDto dto = new SearchProductDto(
                source.getId(),
                source.getProduct_name(),
                source.getProduct_name_ascii(),
                source.getImage_url(),
                source.getInstallment(),
                this.dStorageDetailDto.convert(source.getDefaultStorage()));

        return dto;
    }

}
