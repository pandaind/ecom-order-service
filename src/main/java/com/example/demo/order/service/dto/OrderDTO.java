package com.example.demo.order.service.dto;

import com.example.demo.order.model.EventStatus;
import com.example.demo.order.model.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class OrderDTO implements Serializable {

    private static final long serialVersionUID = -6281737652494349318L;

    @JsonIgnore
    private Long id;
    private String orderId;
    private LocalDate orderDate;
    private OrderStatus status;
    private BigDecimal total;
    private List<ItemDTO> items;
    private UserDTO user;
    @JsonIgnore
    private EventStatus eventStatus = EventStatus.NEW;
}
