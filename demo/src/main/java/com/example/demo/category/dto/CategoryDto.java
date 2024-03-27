package com.example.demo.category.dto;

import java.util.List;

import com.example.demo.brand.dto.BrandDto;
import com.example.demo.price_range.dto.PriceRangeDto;

import lombok.NonNull;


public record CategoryDto(
        @NonNull 
        String category_ascii,
        
        @NonNull 
        String category_name,

        Integer is_show,

        Long id,

        List<BrandDto> brands,

        List<PriceRangeDto> price_ranges,

        CategorySliderDto category_slider
        ) {
}
