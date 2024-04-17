package com.kecoyo.turtlebase.service.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import com.kecoyo.turtlebase.common.security.JwtUserDto;
import com.kecoyo.turtlebase.dto.LoginUserDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapstruct {

    @Mappings({
            @Mapping(target = "id", source = "user.id"),
            @Mapping(target = "username", source = "user.username"),
            @Mapping(target = "name", source = "user.name"),
            @Mapping(target = "avatar", source = "user.avatar"),
            @Mapping(target = "phone", source = "user.phone"),
            @Mapping(target = "gender", source = "user.gender"),
            @Mapping(target = "email", source = "user.email"),
    })
    LoginUserDto toLoginUserDto(JwtUserDto userDetails);
}
