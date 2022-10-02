package com.example.demo.order.web.rest;

import com.example.demo.order.client.InventoryClient;
import com.example.demo.order.client.UserClient;
import com.example.demo.order.event.OrderEventProducer;
import com.example.demo.order.model.Inventory;
import com.example.demo.order.model.Item;
import com.example.demo.order.model.OrderStatus;
import com.example.demo.order.model.User;
import com.example.demo.order.service.CartService;
import com.example.demo.order.service.OrderService;
import com.example.demo.order.service.dto.ItemDTO;
import com.example.demo.order.service.dto.OrderDTO;
import com.example.demo.order.service.dto.UserDTO;
import com.example.demo.order.service.utils.OrderUtils;
import com.example.demo.order.web.rest.util.HeaderUtil;
import com.example.demo.order.web.rest.util.ResponseUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
public class OrderResource {

    private static final String ENTITY_NAME = "Order";

    private final OrderService orderService;

    private final CartService cartService;

    private final UserClient userClient;

    private final InventoryClient inventoryClient;

    private final OrderEventProducer eventProducer;

    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    public OrderResource(OrderService orderService,
                         CartService cartService,
                         UserClient userClient,
                         InventoryClient inventoryClient,
                         OrderEventProducer eventProducer) {
        this.orderService = orderService;
        this.cartService = cartService;
        this.userClient = userClient;
        this.inventoryClient = inventoryClient;
        this.eventProducer = eventProducer;
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrderDTO> getByOrderId(@PathVariable("orderId") String orderId) {
        return ResponseUtil.wrapNotFound(Optional.ofNullable(
                this.orderService.getOrderByOrderId(orderId)
        ));
    }

    @PostMapping("/orders/{userId}")
    @Transactional
    public ResponseEntity<OrderDTO> saveOrder(@PathVariable("userId") long userId, @RequestHeader("Cookie") String cartId)
            throws URISyntaxException, JsonProcessingException {
        List<ItemDTO> cart = this.cartService.getAllItemsFromCart(cartId);
        UserDTO user = this.userClient.getUserById(userId);
        Optional<OrderDTO> orderReq = this.createOrder(cart, user);

        if (orderReq.isPresent()) {
            OrderDTO order = orderReq.get();
            order = this.orderService.saveOrder(order);
            this.cartService.deleteCart(cartId);
            updateInventory(order);
            this.eventProducer.sendEvent(OrderUtils.createOrderEvent(order));
            return ResponseEntity.created(new URI("/orders/" + userId))
                    .headers(HeaderUtil.createEntityCreationAlert(applicationName,
                            false, ENTITY_NAME, order.getId().toString()))
                    .body(order);
        } else {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    private Optional<OrderDTO> createOrder(List<ItemDTO> cart, UserDTO user) {
        if (cart == null || user == null) {
            return Optional.empty();
        }
        cart = cart.stream().filter(this::checkInventory).collect(Collectors.toList());
        if (!cart.isEmpty()) {
            OrderDTO order = new OrderDTO();
            order.setItems(cart);
            order.setUser(user);
            order.setOrderId(OrderUtils.generateOrderId());
            order.setTotal(OrderUtils.countTotalPrice(cart));
            order.setOrderDate(LocalDate.now());
            order.setStatus(OrderStatus.NEW);
            return Optional.of(order);
        }
        return Optional.empty();
    }

    private boolean checkInventory(ItemDTO item) {
        Inventory inventory = this.inventoryClient.getInventory(item.getProduct().getSkuCode());
        return inventory.getQuantity() >= item.getQuantity();
    }

    private void updateInventory(OrderDTO order) {
        order.getItems().forEach(item -> this.inventoryClient.updateInventory(item.getProduct().getSkuCode(),
                item.getQuantity()));
    }


}
