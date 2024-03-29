package com.example.demo.order.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "items")
@Cacheable
public class Item implements Serializable {

    private static final long serialVersionUID = 7289899114017123860L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(name = "quantity")
    @NotNull
    private Long quantity;

    @Column(name = "subtotal")
    @NotNull
    private BigDecimal subTotal;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToMany(mappedBy = "items")
    @JsonIgnore
    private List<Order> orders;

    public Item(@NotNull Long quantity, Product product, BigDecimal subTotal) {
        this.quantity = quantity;
        this.product = product;
        this.subTotal = subTotal;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Item item = (Item) o;

        return Objects.equals(id, item.id);
    }

    @Override
    public int hashCode() {
        return 1986846516;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "quantity = " + quantity + ", " +
                "subTotal = " + subTotal + ", " +
                "product = " + product + ")";
    }
}
