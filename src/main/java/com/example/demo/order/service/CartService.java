package com.example.demo.order.service;

import com.example.demo.order.model.Item;

import java.util.List;

public interface CartService {
    void addItemToCart(String cartId, Long productId, Integer quantity);
    List<Object> getCart(String cartId);
    void changeItemQuantity(String cartId, Long productId, Integer quantity);
    void deleteItemFromCart(String cartId, Long productId);
    boolean checkIfItemIsExist(String cartId, Long productId);
    List<Item> getAllItemsFromCart(String cartId);
    void deleteCart(String cartId);
}
