package com.example.demo.category.dto;

import java.util.List;

import com.example.demo.brand.dto.BrandDto;
import com.example.demo.category_attribute.dto.CategoryAttributeDto;
import com.example.demo.price_range.dto.PriceRangeDto;

import lombok.NonNull;

public record CategoryDto(
                Long id,

                @NonNull String category_name_ascii,

                @NonNull String category_name,

                String attribute_order,

                Integer is_show,

                List<BrandDto> brands,

                List<PriceRangeDto> price_ranges,

                List<CategoryAttributeDto> attributes,

                CategorySliderDto category_slider) {
}
