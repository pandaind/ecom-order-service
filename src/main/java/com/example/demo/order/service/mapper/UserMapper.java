package com.example.demo.order.service.mapper;

import com.example.demo.order.model.User;
import com.example.demo.order.service.dto.UserDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(
        componentModel = "spring",
        uses = {}
)
public interface UserMapper extends EntityMapper<UserDTO, User> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoId(User user);
}
