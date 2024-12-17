package com.example.demo.cart_item.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.cart_item.dto.CartItemDto;
import com.example.demo.cart_item.entity.CartItem;

@Component
public class CartItemToCartItemDto implements Converter<CartItem, CartItemDto> {

    private final ProductToCartProductDto productToCartProductDto;

    public CartItemToCartItemDto(ProductToCartProductDto productToCartProductDto) {
        this.productToCartProductDto = productToCartProductDto;
    }

    @Override
    public CartItemDto convert(CartItem source) {

        CartItemDto dto = new CartItemDto(
                source.getId(),
                source.getUsername(),
                source.getColorId(),
                source.getStorageId(),
                source.getAmount(),
                source.getProduct_id(),
                this.productToCartProductDto.convert(source.getProduct()));

        return dto;
    }

}
