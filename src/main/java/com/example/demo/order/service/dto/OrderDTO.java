package com.example.demo.order.service.dto;

import com.example.demo.order.model.Item;
import com.example.demo.order.model.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class OrderDTO {
    private Long id;
    private LocalDate orderDate;
    private String status;
    private BigDecimal total;
    private List<Item> items;
    private User user;
}
