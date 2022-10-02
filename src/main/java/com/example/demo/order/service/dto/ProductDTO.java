package com.example.demo.order.service.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ProductDTO {
    private Long productId;

    private Long id;

    private String productName;

    private BigDecimal price;

    private String category;

    private String skuCode;
}
