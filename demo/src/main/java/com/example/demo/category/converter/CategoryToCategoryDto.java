
package com.example.demo.category.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.brand.converter.BrandToBrandDto;
import com.example.demo.brand.dto.BrandDto;
import com.example.demo.brand.entity.Brand;
import com.example.demo.category.dto.CategoryDto;
import com.example.demo.category.entity.Category;
import com.example.demo.category_attribute.converter.CategoryAttToCategoryAttDto;
import com.example.demo.price_range.converter.PriceRangeToPriceRangeDto;
import com.example.demo.price_range.dto.PriceRangeDto;
import com.example.demo.price_range.entity.PriceRange;

@Component
public class CategoryToCategoryDto implements Converter<Category, CategoryDto> {

    private final BrandToBrandDto brandToBrandDto;
    private final CategorySliderToCategorySliderDto categorySliderToCategorySliderDto;
    private final PriceRangeToPriceRangeDto priceRangeToPriceRangeDto;
    private final CategoryAttToCategoryAttDto categoryAttToCategoryAttDto;

    public CategoryToCategoryDto(
            PriceRangeToPriceRangeDto priceRangeToPriceRangeDto,
            BrandToBrandDto brandToBrandDto,
            CategorySliderToCategorySliderDto categorySliderToCategorySliderDto,
            CategoryAttToCategoryAttDto categoryAttToCategoryAttDto) {
        this.brandToBrandDto = brandToBrandDto;
        this.categorySliderToCategorySliderDto = categorySliderToCategorySliderDto;
        this.priceRangeToPriceRangeDto = priceRangeToPriceRangeDto;
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

    List<PriceRangeDto> getPriceRangeDto(List<PriceRange> priceRanges) {
        ArrayList<PriceRangeDto> priceRangesDto = new ArrayList<>();
        for (PriceRange priceRange : priceRanges) {
            PriceRangeDto priceRangeDto = this.priceRangeToPriceRangeDto.convert(priceRange);
            priceRangesDto.add(priceRangeDto);
        }

        return priceRangesDto;
    }

    @Override
    public CategoryDto convert(Category source) {
        CategoryDto categoryDto = new CategoryDto(
            source.getId(),
                source.getCategory_ascii(),
                source.getCategory_name(),
                source.getAttribute_order(),
                source.getIs_show(),
                source.getBrands().isEmpty()
                        ? new ArrayList<BrandDto>()
                        : getBrandsDto(source.getBrands()),
                source.getPriceRanges().isEmpty()
                        ? new ArrayList<PriceRangeDto>()
                        : getPriceRangeDto(source.getPriceRanges()),
                source.getCategoryAttributes().isEmpty()
                        ? new ArrayList<>()
                        : source.getCategoryAttributes().stream()
                                .map(catAttr -> this.categoryAttToCategoryAttDto.convert(catAttr)).toList(),
                source.getCategorySlider() != null
                        ? this.categorySliderToCategorySliderDto.convert(source.getCategorySlider())
                        : null);
        return categoryDto;
    }
}