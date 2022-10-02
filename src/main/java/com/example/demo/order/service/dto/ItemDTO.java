package com.example.demo.order.service.dto;

import com.example.demo.order.model.Product;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ItemDTO {
    private Long id;

    private Long quantity;

    private BigDecimal subTotal;

    private ProductDTO product;
}
