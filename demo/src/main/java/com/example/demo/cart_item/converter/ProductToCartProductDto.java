package com.example.demo.cart_item.converter;

import java.util.ArrayList;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.cart_item.dto.CartProductDto;
import com.example.demo.color.converter.ColorToColorDto;
import com.example.demo.product.entity.Product;
import com.example.demo.storage.converter.StorageToStorageDto;


@Component
public class ProductToCartProductDto implements Converter<Product, CartProductDto> {

    private final StorageToStorageDto storageToStorageDto;

    private final ColorToColorDto colorToColorDto;

    public ProductToCartProductDto(StorageToStorageDto storageToStorageDto,

            ColorToColorDto colorToColorDto) {

        this.storageToStorageDto = storageToStorageDto;
        this.colorToColorDto = colorToColorDto;
    }

    @Override
    public CartProductDto convert(Product source) {

        CartProductDto dto = new CartProductDto(
                source.getProduct_name(),
                source.getCategoryId(),
                source.getBrandId(),
                source.getImage_url(),
                source.getInstallment(),
                source.getStorages().isEmpty()
                        ? new ArrayList<>()
                        : source.getStorages().stream().map(
                                storage -> this.storageToStorageDto.convert(storage)).toList(),
                source.getColors().isEmpty()
                        ? new ArrayList<>()
                        : source.getColors().stream().map(
                                color -> this.colorToColorDto.convert(color)).toList());

        return dto;
    }

}
