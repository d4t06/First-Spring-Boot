
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

@Component
public class CategoryToCategoryDto implements Converter<Category, CategoryDto> {

    private final BrandToBrandDto brandToBrandDto;

    public CategoryToCategoryDto(BrandToBrandDto brandToBrandDto) {
        this.brandToBrandDto = brandToBrandDto;
    }

    List<BrandDto> getBrandDtos(List<Brand> brands) {
        ArrayList<BrandDto> brandDtos = new ArrayList<>();
        for (Brand brand : brands) {
            BrandDto brandDto = this.brandToBrandDto.convert(brand);
            brandDtos.add(brandDto);
        }

        return brandDtos;
    }

    @Override
    public CategoryDto convert(Category source) {
        CategoryDto categoryDto = new CategoryDto(
                source.getCategory_ascii(),
                source.getCategory_name(),
                source.getId(),
                source.getBrands().isEmpty()
                        ? new ArrayList<BrandDto>()
                        : getBrandDtos(source.getBrands()));
        return categoryDto;
    }
}