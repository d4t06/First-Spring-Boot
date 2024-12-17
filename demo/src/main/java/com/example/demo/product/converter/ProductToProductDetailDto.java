package com.example.demo.product.converter;

import java.util.ArrayList;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.category.converter.CategoryToProductCategoryDto;
import com.example.demo.color.converter.ColorToColorDto;
import com.example.demo.combine.converter.CombineToCombineDto;
import com.example.demo.default_storage.converter.DefaultStorageToDefaultStorageDto;
import com.example.demo.description.converter.ProductDescToProductDescDto;
import com.example.demo.product.dto.ProductDetailDto;
import com.example.demo.product.entity.Product;
import com.example.demo.product_attribute.converter.ProductAttributeToProductAttributeDto;
import com.example.demo.product_slider.converter.ProductToProductSliderDto;
import com.example.demo.storage.converter.StorageToStorageDto;

@Component
public class ProductToProductDetailDto implements Converter<Product, ProductDetailDto> {

        private final StorageToStorageDto storageToStorageDto;
        private final ColorToColorDto colorToColorDto;
        private final CombineToCombineDto combineToCombineDto;
        private final ProductAttributeToProductAttributeDto productAttrToProductAttrDto;
        private final CategoryToProductCategoryDto categoryToProductCategoryDto;
        private final ProductDescToProductDescDto productDescToProductDescDto;
        private final DefaultStorageToDefaultStorageDto defaultStorageToDefaultStorageDto;

        public ProductToProductDetailDto(
                        StorageToStorageDto storageToStorageDto,
                        ColorToColorDto colorToColorDto,
                        CombineToCombineDto combineToCombineDto,
                        CategoryToProductCategoryDto categoryToProductCategoryDto,
                        ProductDescToProductDescDto productDescToProductDescDto,
                        ProductAttributeToProductAttributeDto productAttrToProductAttrDto,
                        DefaultStorageToDefaultStorageDto defaultStorageToDefaultStorageDto,
                        ProductToProductSliderDto productToProductSliderDto) {
                this.colorToColorDto = colorToColorDto;
                this.storageToStorageDto = storageToStorageDto;
                this.combineToCombineDto = combineToCombineDto;
                this.productAttrToProductAttrDto = productAttrToProductAttrDto;
                this.categoryToProductCategoryDto = categoryToProductCategoryDto;
                this.productDescToProductDescDto = productDescToProductDescDto;
                this.defaultStorageToDefaultStorageDto = defaultStorageToDefaultStorageDto;
        }

        @Override
        public ProductDetailDto convert(Product source) {
                ProductDetailDto productDetailDto = new ProductDetailDto(
                                source.getId(),
                                source.getProduct_name(),
                                source.getProduct_name_ascii(),
                                source.getCategoryId(),
                                this.categoryToProductCategoryDto.convert(source.getCategory()),
                                source.getBrandId(),
                                source.getImage_url(),
                                source.getStorages().isEmpty()
                                                ? new ArrayList<>()
                                                : source.getStorages().stream().map(
                                                                storage -> this.storageToStorageDto.convert(storage))
                                                                .toList(),
                                source.getColors().isEmpty()
                                                ? new ArrayList<>()
                                                : source.getColors().stream()
                                                                .map(color -> this.colorToColorDto.convert(color))
                                                                .toList(),
                                source.getCombines().isEmpty()
                                                ? new ArrayList<>()
                                                : source.getCombines().stream()
                                                                .map(combine -> this.combineToCombineDto
                                                                                .convert(combine))
                                                                .toList(),
                                source.getAttributes().isEmpty()
                                                ? new ArrayList<>()
                                                : source.getAttributes().stream()
                                                                .map(attr -> this.productAttrToProductAttrDto
                                                                                .convert(attr))
                                                                .toList(),
                                this.productDescToProductDescDto.convert(source.getProductDescription()),
                                this.defaultStorageToDefaultStorageDto.convert(source.getDefaultStorage()),
                                false);

                return productDetailDto;
        }

}
