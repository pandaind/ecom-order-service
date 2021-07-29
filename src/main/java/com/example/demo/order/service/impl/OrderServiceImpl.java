package com.example.demo.order.service.impl;

import com.example.demo.order.model.Order;
import com.example.demo.order.repository.OrderRepository;
import com.example.demo.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;

    @Autowired
    public OrderServiceImpl(OrderRepository repository){
        this.repository = repository;
    }

    @Override
    public Order saveOrder(Order order) {
        return this.repository.save(order);
    }
}
