package com.example.demo.order.service;

import com.example.demo.order.model.Cart;
import com.example.demo.order.model.Item;

import java.util.List;
import java.util.Optional;

public interface CartService {
    Cart save(Cart cart);

    Optional<Cart> getCart(String cartId);

    Cart addOrUpdate(String cartId, Long productId, Long quantity);

    void deleteItemFromCart(String cartId, Long productId);

    boolean checkIfItemIsExist(String cartId, Long productId);

    List<Item> getAllItemsFromCart(String cartId);

    void deleteCart(String cartId);
}
