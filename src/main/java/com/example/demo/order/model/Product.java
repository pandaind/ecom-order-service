package com.example.demo.order.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "products")
@Cacheable
public class Product implements Serializable {
    private static final long serialVersionUID = -818184442419043799L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long productId;

    private Long id;

    private String productName;

    private BigDecimal price;

    private String category;

    private String skuCode;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Item> items;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Product product = (Product) o;

        return Objects.equals(productId, product.productId);
    }

    @Override
    public int hashCode() {
        return 2042274511;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", id=" + id +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", skuCode='" + skuCode + '\'' +
                ", items=" + items +
                '}';
    }
}
