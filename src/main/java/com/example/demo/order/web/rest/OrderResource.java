package com.example.demo.order.web.rest;

import com.example.demo.order.client.UserClient;
import com.example.demo.order.model.Item;
import com.example.demo.order.model.User;
import com.example.demo.order.service.CartService;
import com.example.demo.order.service.OrderService;
import com.example.demo.order.service.dto.OrderDTO;
import com.example.demo.order.service.utils.OrderUtils;
import com.example.demo.order.web.rest.util.HeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
public class OrderResource {

    private static final String ENTITY_NAME = "Order";

    private final OrderService orderService;

    private final CartService cartService;

    private final UserClient userClient;

    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    public OrderResource(OrderService orderService, CartService cartService, UserClient userClient) {
        this.orderService = orderService;
        this.cartService = cartService;
        this.userClient = userClient;
    }

    @PostMapping("/orders/{userId}")
    public ResponseEntity<Optional<OrderDTO>> saveOrder(@PathVariable("userId") long userId, @RequestHeader("Cookie") String cartId) throws URISyntaxException {
        List<Item> cart = this.cartService.getAllItemsFromCart(cartId);
        User user = this.userClient.getUserById(userId);
        Optional<OrderDTO> order = this.createOrder(cart, user);

        if (order.isPresent()) {
            this.orderService.saveOrder(order.get());
            this.cartService.deleteCart(cartId);

            return ResponseEntity.created(new URI("/orders/" + userId))
                    .headers(HeaderUtil.createEntityCreationAlert(applicationName,
                            false, ENTITY_NAME, order.get().getId().toString()))
                    .body(order);
        } else {
            return ResponseEntity.unprocessableEntity().build();
        }
    }


    private Optional<OrderDTO> createOrder(List<Item> cart, User user) {
        if (cart == null || user == null) {
            return Optional.empty();
        }
        OrderDTO order = new OrderDTO();
        order.setItems(cart);
        order.setUser(user);
        order.setTotal(OrderUtils.countTotalPrice(cart));
        order.setOrderDate(LocalDate.now());
        order.setStatus("PAYMENT_EXPECTED");
        return Optional.of(order);
    }


}
