package com.example.demo.order.service.impl;

import com.example.demo.order.model.Order;
import com.example.demo.order.repository.OrderRepository;
import com.example.demo.order.service.OrderService;
import com.example.demo.order.service.dto.OrderDTO;
import com.example.demo.order.service.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;

    private final OrderMapper mapper;

    @Autowired
    public OrderServiceImpl(OrderRepository repository, OrderMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public OrderDTO saveOrder(OrderDTO orderDTO) {
        log.debug("Request to save Order : {}", orderDTO);
        Order order = this.mapper.toEntity(orderDTO);
        order = this.repository.save(order);
        return this.mapper.toDto(order);
    }
}
