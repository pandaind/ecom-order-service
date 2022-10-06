package com.example.demo.order.service.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ProductDTO implements Serializable {
    private static final long serialVersionUID = -1750865931871837734L;

    private Long productId;

    private Long id;

    private String productName;

    private BigDecimal price;

    private String category;

    private String skuCode;
}
