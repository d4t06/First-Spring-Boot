package com.example.demo.product.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

import com.example.demo.category.dto.ProductCategoryDto;
import com.example.demo.color.dto.ColorDto;
import com.example.demo.combine.dto.CombineDto;
import com.example.demo.default_storage.dto.DefaultStorageDto;
import com.example.demo.description.dto.ProductDescDto;
import com.example.demo.product_attribute.dto.ProductAttributeDto;
import com.example.demo.storage.dto.StorageDto;

public record ProductDetailDto(
        // Long id,

        @NotEmpty(message = "name is required") String product_name,

        @NotEmpty(message = "ascii is required") String product_ascii,

        @NotNull(message = "category is required") Long category_id,

        ProductCategoryDto category,

        @NotNull(message = "brand is required") Long brand_id,

        String image_url,

        @NotNull(message = "price is required") int price,

        List<StorageDto> storages,

        List<ColorDto> colors,

        List<CombineDto> combines,

        List<ProductAttributeDto> attributes,

        ProductDescDto description,

        DefaultStorageDto default_storage,

        boolean installment) {
}
