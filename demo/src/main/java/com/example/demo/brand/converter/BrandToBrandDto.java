package com.example.demo.brand.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.brand.dto.BrandDto;
import com.example.demo.brand.entity.Brand;

@Component
public class BrandToBrandDto implements Converter<Brand, BrandDto> {

    @Override
    public BrandDto convert(Brand source) {
        BrandDto brandDto = new BrandDto(
                source.getId(),
                source.getBrand_name(),
                source.getBrand_name_ascii(),
                source.getCategory_id());

        return brandDto;
    }

}
