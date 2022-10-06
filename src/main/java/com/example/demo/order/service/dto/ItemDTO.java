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
public class ItemDTO implements Serializable {
    private static final long serialVersionUID = -1741513296626689968L;

    private Long id;

    private Long quantity;

    private BigDecimal subTotal;

    private ProductDTO product;
}
