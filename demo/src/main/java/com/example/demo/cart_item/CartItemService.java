package com.example.demo.cart_item;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.cart_item.converter.CartItemDtoToCartItem;
import com.example.demo.cart_item.converter.CartItemToCartItemDto;
import com.example.demo.cart_item.dto.CartItemDetailDto;
import com.example.demo.cart_item.dto.CartItemDto;
import com.example.demo.cart_item.entity.CartItem;
import com.example.demo.combine.CombineRepository;
import com.example.demo.combine.entity.Combine;
import com.example.demo.system.MyResponse;
import com.example.demo.system.exception.ObjectNotFoundException;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    private final CombineRepository combineRepository;

    private final CartItemDtoToCartItem cartItemDtoToCartItem;

    private final CartItemToCartItemDto cartItemToCartItemDto;

    public CartItemService(CartItemRepository cartItemRepository,
            CombineRepository combineRepository,
            CartItemToCartItemDto cartItemToCartItemDto,
            CartItemDtoToCartItem cartItemDtoToCartItem) {
        this.cartItemRepository = cartItemRepository;
        this.combineRepository = combineRepository;
        this.cartItemDtoToCartItem = cartItemDtoToCartItem;
        this.cartItemToCartItemDto = cartItemToCartItemDto;
    }

    public MyResponse findAll(String username) {

        List<CartItem> cartItems = cartItemRepository.findByUsername(username);

        List<CartItemDetailDto> cartItemDetailsDto = cartItems.stream().map(cartItem -> {

            CartItemDto cartItemDto = this.cartItemToCartItemDto.convert(cartItem);
            Combine combine = combineRepository.findByStorageIdAndColorId(
                    cartItemDto.storage_id(),
                    cartItemDto.color_id())
                    .orElseThrow(() -> new ObjectNotFoundException(""));

            return new CartItemDetailDto(cartItemDto, combine.getPrice());
        }).toList();

        return new MyResponse(true, "get all cart successful", 200, cartItemDetailsDto);
    }

    public MyResponse create(CartItemDto cartItemDto) {

        Optional<CartItem> existingCartItem = this.cartItemRepository.findByUsernameAndColorIdAndStorageId(
                cartItemDto.username(), cartItemDto.color_id(), cartItemDto.storage_id());

        if (existingCartItem.isPresent())
            return new MyResponse(true,
                    "Create cart item successful",
                    200, existingCartItem.get().getId());

        CartItem cartItem = this.cartItemDtoToCartItem.convert(cartItemDto);
        CartItem newCartItem = this.cartItemRepository.save(cartItem);

        return new MyResponse(true,
                "Create cart item successful",
                200, newCartItem.getId());
    }

    public MyResponse update(Long id, CartItemDto cartItemDto) {

        CartItem cartItem = this.cartItemRepository.findById(id)
                .map(old -> {
                    old.setAmount(cartItemDto.amount());
                    old.setStorageId(cartItemDto.storage_id());
                    old.setColorId(cartItemDto.color_id());

                    CartItem updated = this.cartItemRepository.save(old);
                    return updated;
                })
                .orElseThrow(() -> new ObjectNotFoundException("CartItem not found"));

        Combine combine = combineRepository.findByStorageIdAndColorId(
                cartItem.getStorageId(),
                cartItem.getColorId())
                .orElseThrow(() -> new ObjectNotFoundException("CartItem not found"));

        return new MyResponse(true, "update cart item successful", 200, combine.getPrice());
    }

    public MyResponse delete(Long id) {
        this.cartItemRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("CartItem not found"));

        this.cartItemRepository.deleteById(id);

        return new MyResponse(true, "delete cart item successful", 200);
    }

}
