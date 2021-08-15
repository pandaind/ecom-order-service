package com.example.demo.order.service;

import com.example.demo.order.service.dto.OrderDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface OrderService {
    OrderDTO saveOrder(OrderDTO orderDTO) throws JsonProcessingException;

    OrderDTO getOrderByOrderId(String orderId);

    OrderDTO updateOrder(OrderDTO orderDTO);
}
