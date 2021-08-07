package com.example.demo.order.service;

import com.example.demo.order.service.dto.OrderDTO;

public interface OrderService {
    OrderDTO saveOrder(OrderDTO orderDTO);
}
