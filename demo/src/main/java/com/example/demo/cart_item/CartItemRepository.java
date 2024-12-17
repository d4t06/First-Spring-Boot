package com.example.demo.cart_item;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.cart_item.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUsername(String username);

    Optional<CartItem> findByUsernameAndColorIdAndStorageId(String username, Long color_id, Long storage_id);
}
