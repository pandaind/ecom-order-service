package com.example.demo.order.service.mapper;

import com.example.demo.order.model.Order;
import com.example.demo.order.service.dto.OrderDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(
        componentModel = "spring",
        uses = {}
)
public interface OrderMapper extends EntityMapper<OrderDTO, Order> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrderDTO toDtoId(Order order);
}
