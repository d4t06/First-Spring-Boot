package com.example.demo.cart_item;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.cart_item.dto.CartItemDto;
import com.example.demo.system.MyResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cart-items")
public class CartItemController {
    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @GetMapping("/{username}")
    public MyResponse findAll(@PathVariable String username) {
        return this.cartItemService.findAll(username);
    }

    @PostMapping("")
    public MyResponse create(@Valid @RequestBody CartItemDto cartItemDto) {
        return this.cartItemService.create(cartItemDto);

    }

    @PutMapping("/{id}")
    public MyResponse replace(
            @PathVariable Long id,
            @RequestBody CartItemDto cartItemDto) {

        return this.cartItemService.update(id, cartItemDto);

    }

    @DeleteMapping("/{id}")
    public MyResponse delete(
            @PathVariable Long id) {

        return this.cartItemService.delete(id);
    }

}
