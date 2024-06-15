package com.example.demo.cart_item.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.cart_item.dto.CartItemDto;
import com.example.demo.cart_item.entity.CartItem;


@Component
public class CartItemDtoToCartItem implements Converter<CartItemDto, CartItem> {

    @Override
    public CartItem convert(CartItemDto source) {

        CartItem cartItem = new CartItem();

        cartItem.setUsername(source.username());
        cartItem.setProduct_ascii(source.product_ascii());
        cartItem.setColorId(source.color_id());
        cartItem.setStorageId(source.storage_id());
        cartItem.setAmount(source.amount());

        return cartItem;
    }

}
