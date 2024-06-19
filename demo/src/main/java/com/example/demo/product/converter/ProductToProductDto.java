package com.example.demo.product.converter;

import java.util.ArrayList;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.default_storage.converter.DefaultStorageToDefaultStorageDto;
import com.example.demo.product.dto.ProductDTO;
import com.example.demo.product.entity.Product;
import com.example.demo.storage.converter.StorageToStorageDetailDto;

@Component
public class ProductToProductDto implements Converter<Product, ProductDTO> {

    private final StorageToStorageDetailDto storageToStorageDetailDto;
    private final DefaultStorageToDefaultStorageDto defaultStorageToDefaultStorageDto;

    public ProductToProductDto(StorageToStorageDetailDto storageToStorageDetailDto,
            DefaultStorageToDefaultStorageDto defaultStorageToDefaultStorageDto) {

        this.storageToStorageDetailDto = storageToStorageDetailDto;
        this.defaultStorageToDefaultStorageDto = defaultStorageToDefaultStorageDto;
    }

    @Override
    public ProductDTO convert(Product source) {
        ProductDTO productDto = new ProductDTO(
                source.getId(),
                source.getProduct_name(),
                source.getProduct_name_ascii(),
                source.getCategoryId(),
                source.getBrandId(),
                source.getImage_url(),
                source.getInstallment(),
                source.getStorages().isEmpty()
                        ? new ArrayList<>()
                        : source.getStorages().stream().map(
                                storage -> this.storageToStorageDetailDto.convert(storage)).toList(),
                this.defaultStorageToDefaultStorageDto.convert(source.getDefaultStorage()));

        return productDto;
    }

}
