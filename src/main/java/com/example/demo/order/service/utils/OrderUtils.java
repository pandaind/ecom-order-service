package com.example.demo.order.service.utils;

import com.example.demo.order.model.Item;

import java.math.BigDecimal;
import java.util.List;

public class OrderUtils {
    public static BigDecimal countTotalPrice(List<Item> cart) {
        return cart.parallelStream()
                .map(Item::getSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
