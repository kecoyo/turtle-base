package com.kecoyo.turtleopen.service.mapstruct;

import org.mapstruct.Mapper;

import com.kecoyo.turtleopen.domain.dto.UserLoginDto;
import com.kecoyo.turtleopen.domain.entity.User;

@Mapper(componentModel = "spring")
public interface BeanMapper {

    UserLoginDto toUserLoginDto(User user);

}
