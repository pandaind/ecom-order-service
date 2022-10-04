package com.example.demo.order.service.impl;

import com.example.demo.order.model.Order;
import com.example.demo.order.model.User;
import com.example.demo.order.repository.OrderRepository;
import com.example.demo.order.repository.UserRepository;
import com.example.demo.order.service.OrderService;
import com.example.demo.order.service.dto.OrderDTO;
import com.example.demo.order.service.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    private final OrderMapper mapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            UserRepository userRepository,
                            OrderMapper mapper) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public OrderDTO saveOrder(OrderDTO orderDTO) {
        log.debug("Request to save Order : {}", orderDTO);
        Order order = this.mapper.toEntity(orderDTO);
        // save the user
        User user = this.userRepository.save(order.getUser());

        //add persisted users to order
        order.setUser(user);
        order = this.orderRepository.save(order);
        return this.mapper.toDto(order);
    }

    @Override
    @Cacheable(key = "#orderId", value = "Order", unless = "result.total > 100000")
    public OrderDTO getOrderByOrderId(String orderId) {
        log.debug("Get Order by id : {}", orderId);
        return this.mapper.toDto(this.orderRepository.findByOrderId(orderId));
    }

    @Override
    @CachePut(key = "#orderId", value = "Order")
    public OrderDTO updateOrder(OrderDTO orderDTO) {
        log.debug("Request to save Order : {}", orderDTO);
        Order order = this.mapper.toEntity(orderDTO);
        order = this.orderRepository.save(order);
        return this.mapper.toDtoId(order);
    }
}
