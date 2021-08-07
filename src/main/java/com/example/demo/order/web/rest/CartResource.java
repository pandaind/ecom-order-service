package com.example.demo.order.web.rest;

import com.example.demo.order.model.Cart;
import com.example.demo.order.service.CartService;
import com.example.demo.order.web.rest.util.HeaderUtil;
import com.example.demo.order.web.rest.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
public class CartResource {

    private static final String ENTITY_NAME = "Cart";
    private final CartService service;
    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    public CartResource(CartService service) {
        this.service = service;
    }

    @GetMapping("/cart")
    public ResponseEntity<Optional<Cart>> getCart(@RequestHeader("Cookie") String cartId) {
        Optional<Cart> cart = this.service.getCart(cartId);
        return ResponseUtil.wrapNotFound(Optional.ofNullable(cart));
    }

    @PostMapping(value = "/cart", params = {"productId", "quantity"})
    public ResponseEntity<Optional<Cart>> addItemToCart(@RequestParam("productId") Long productId,
                                                        @RequestParam("quantity") Integer quantity,
                                                        @RequestHeader(value = "Cookie") String cartId) throws URISyntaxException {

        Optional<Cart> cart = Optional.ofNullable(this.service.addOrUpdate(cartId, productId, quantity));

        return ResponseEntity.created(new URI("/cart"))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName,
                        false, ENTITY_NAME, cartId))
                .body(cart);
    }

    @DeleteMapping(value = "/cart", params = "productId")
    public ResponseEntity<Void> removeItemFromCart(Long productId, @RequestHeader(value = "Cookie") String cartId) {
        this.service.deleteItemFromCart(cartId, productId);
        return ResponseEntity.noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName,
                        false, ENTITY_NAME, productId.toString())).build();
    }


}
