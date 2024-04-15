package com.kecoyo.turtlebase.service.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.kecoyo.turtlebase.dto.UserDto;
import com.kecoyo.turtlebase.model.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapstruct {

    UserDto toDto(User user);
}
