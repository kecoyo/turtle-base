package com.kecoyo.turtlebase.service.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.kecoyo.turtlebase.domain.User;
import com.kecoyo.turtlebase.domain.dto.UserDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapstruct {

    UserDto toDto(User user);
}
