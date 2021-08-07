package com.example.demo.order.service.utils;

import com.example.demo.order.model.Cart;
import com.example.demo.order.model.Item;
import com.example.demo.order.model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CartUtils {
    public static BigDecimal getSubTotalForItem(Product product, int quantity) {
        return (product.getPrice()).multiply(BigDecimal.valueOf(quantity));
    }

    public static Cart cartWithTotal(Cart cart) {
        BigDecimal total = cart.getItems().stream().map(Item::getSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotal(total);
        return cart;
    }

    public static Cart addItemToCart(String cartId, Item item) {
        List<Item> items = new ArrayList<>();
        items.add(item);

        Cart cart = new Cart();
        cart.setId(cartId);
        cart.setItems(items);
        return cart;
    }

    public static Cart addItemToCart(Cart cart, Item item) {
        List<Item> items = cart.getItems();
        items.add(item);
        cart.setItems(items);
        return cart;
    }

    public static Cart addItemToCart(Cart cart, List<Item> items) {
        cart.setItems(items);
        return cart;
    }
}
