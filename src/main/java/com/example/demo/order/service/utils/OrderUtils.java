package com.example.demo.order.service.utils;

import com.example.demo.order.event.OrderEvent;
import com.example.demo.order.event.OrderEventType;
import com.example.demo.order.model.Item;
import com.example.demo.order.service.dto.ItemDTO;
import com.example.demo.order.service.dto.OrderDTO;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class OrderUtils {
    public static BigDecimal countTotalPrice(List<ItemDTO> cart) {
        return cart.parallelStream()
                .map(ItemDTO::getSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static String generateOrderId() {
        return RandomStringUtils.random(8, true, true);
    }

    public static OrderEvent createOrderEvent(OrderDTO order) {
        OrderEvent event = new OrderEvent();
        event.setEventId(UUID.randomUUID().toString());
        event.setType(OrderEventType.NEW);
        event.setOrder(order);
        return event;
    }
}
