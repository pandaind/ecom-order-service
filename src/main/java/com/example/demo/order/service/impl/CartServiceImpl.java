package com.example.demo.order.service.impl;

import com.example.demo.order.model.Item;
import com.example.demo.order.service.CartService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Override
    public void addItemToCart(String cartId, Long productId, Integer quantity) {

    }

    @Override
    public List<Object> getCart(String cartId) {
        return null;
    }

    @Override
    public void changeItemQuantity(String cartId, Long productId, Integer quantity) {

    }

    @Override
    public void deleteItemFromCart(String cartId, Long productId) {

    }

    @Override
    public boolean checkIfItemIsExist(String cartId, Long productId) {
        return false;
    }

    @Override
    public List<Item> getAllItemsFromCart(String cartId) {
        return null;
    }

    @Override
    public void deleteCart(String cartId) {

    }
}
