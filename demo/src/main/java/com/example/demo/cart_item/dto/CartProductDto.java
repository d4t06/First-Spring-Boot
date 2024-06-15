package com.example.demo.cart_item.dto;

import java.util.List;

import com.example.demo.color.dto.ColorDto;
import com.example.demo.storage.dto.StorageDto;

public record CartProductDto(
        String product_name,
        String product_ascii,
        Long category_id,
        Long brand_id,
        String image_url,
        boolean installment,
        List<StorageDto> storages,
        List<ColorDto> colors) {
}
