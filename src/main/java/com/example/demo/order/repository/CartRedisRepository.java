package com.example.demo.order.repository;

import com.example.demo.order.model.Cart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRedisRepository extends CrudRepository<Cart, String> {
}
