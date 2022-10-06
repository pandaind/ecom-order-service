package com.example.demo.order.service.dto;

import com.example.demo.order.model.Order;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserDTO implements Serializable {

    private static final long serialVersionUID = -3254485353656433328L;

    private Long id;

    private String userName;

    private List<Order> orders;
}
