package com.kecoyo.turtlebase.service.mapstruct;

import org.mapstruct.Mapper;

import com.kecoyo.turtlebase.dto.UserLoginDto;
import com.kecoyo.turtlebase.model.User;

@Mapper(componentModel = "spring")
public interface BeanMapper {

    UserLoginDto toUserLoginDto(User user);

}
