package com.example.demo.order.service.dto;

import com.example.demo.order.model.Order;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserDTO {
    private Long id;

    private String userName;

    private List<Order> orders;
}
