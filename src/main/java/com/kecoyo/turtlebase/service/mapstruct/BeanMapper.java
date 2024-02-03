package com.kecoyo.turtlebase.service.mapstruct;

import org.mapstruct.Mapper;

import com.kecoyo.turtlebase.domain.dto.UserLoginDto;
import com.kecoyo.turtlebase.domain.entity.User;

@Mapper(componentModel = "spring")
public interface BeanMapper {

    UserLoginDto toUserLoginDto(User user);

}
