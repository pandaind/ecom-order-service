package com.example.demo.order.service.impl;

import com.example.demo.order.client.ProductClient;
import com.example.demo.order.model.Cart;
import com.example.demo.order.model.Item;
import com.example.demo.order.model.Product;
import com.example.demo.order.repository.CartRedisRepository;
import com.example.demo.order.service.CartService;
import com.example.demo.order.service.utils.CartUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.demo.order.service.utils.CartUtils.addItemToCart;
import static com.example.demo.order.service.utils.CartUtils.getSubTotalForItem;

@Slf4j
@Service
public class CartServiceImpl implements CartService {

    private final ProductClient productClient;
    private final CartRedisRepository cartRedisRepository;

    @Autowired
    public CartServiceImpl(ProductClient productClient, CartRedisRepository cartRedisRepository) {
        this.productClient = productClient;
        this.cartRedisRepository = cartRedisRepository;
    }

    @Override
    public Cart save(Cart cart) {
        return this.cartRedisRepository.save(CartUtils.cartWithTotal(cart));
    }

    @Override
    public Optional<Cart> getCart(String cartId) {
        Optional<Cart> cart = this.cartRedisRepository.findById(cartId);
        if (cart.isPresent()) {
            cart = refreshCart(cart.get());
        }
        return cart;
    }

    @Override
    public Cart addOrUpdate(String cartId, Long productId, Integer quantity) {
        Optional<Cart> cart = this.cartRedisRepository.findById(cartId);

        if (cart.isPresent()) {
            List<Item> items = cart.get().getItems();

            // find the matching item
            Optional<Item> existingItem = items.parallelStream()
                    .filter(item -> (item.getProduct().getId()).equals(productId))
                    .findFirst();

            existingItem.ifPresent(i ->
                items.removeIf(item -> item.getProduct().getId().equals(i.getProduct().getId()))
            );

            items.add(getNewItemWithProductDetails(productId, quantity));

            return save(addItemToCart(cart.get(), items));
        }

        return save(addItemToCart(cartId, getNewItemWithProductDetails(productId, quantity)));
    }

    private Optional<Cart> refreshCart(Cart cart) {
        if (cart!=null && cart.getItems()!=null) {
            List<Item> items = cart.getItems()
                    .parallelStream()
                    .map(item -> getNewItemWithProductDetails(item.getProduct().getId(), item.getQuantity()))
                    .collect(Collectors.toList());
            return Optional.ofNullable(save(addItemToCart(cart, items)));
        }
        return Optional.empty();
    }

    private Item getNewItemWithProductDetails(Long productId, Integer quantity) {
        Product product = productClient.getProductById(productId);
        return new Item(quantity, product, getSubTotalForItem(product, quantity));
    }

    @Override
    public void deleteItemFromCart(String cartId, Long productId) {
        Optional<Cart> cart = this.cartRedisRepository.findById(cartId);
        if (cart.isPresent()) {
            List<Item> items = cart.get().getItems();
            for (Item item : cart.get().getItems()) {
                if ((item.getProduct().getId()).equals(productId)) {
                    items.remove(item);
                }
            }
        }
    }

    @Override
    public boolean checkIfItemIsExist(String cartId, Long productId) {
        Optional<Cart> cart = this.cartRedisRepository.findById(cartId);
        return cart.map(value -> value.getItems().stream()
                .anyMatch(item -> (item.getProduct().getId()).equals(productId))).orElse(false);
    }

    @Override
    public List<Item> getAllItemsFromCart(String cartId) {
        Optional<Cart> cart = this.cartRedisRepository.findById(cartId);
        return cart.orElse(new Cart()).getItems();
    }

    @Override
    public void deleteCart(String cartId) {
        this.cartRedisRepository.deleteById(cartId);
    }
}
