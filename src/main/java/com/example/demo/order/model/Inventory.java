package com.example.demo.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Inventory implements Serializable {
    private static final long serialVersionUID = -3529980536015130580L;

    private String skuCode;
    private Long quantity;
}
