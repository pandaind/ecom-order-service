package com.example.demo.order.service.utils;

import com.example.demo.order.model.Item;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.util.List;

public class OrderUtils {
    public static BigDecimal countTotalPrice(List<Item> cart) {
        return cart.parallelStream()
                .map(Item::getSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static String generateOrderId() {
        return RandomStringUtils.random(8, true, true);
    }
}
