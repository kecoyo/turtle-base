package com.kecoyo.turtleopen.service.mapstruct;

import com.kecoyo.turtleopen.domain.dto.UserDto;
import com.kecoyo.turtleopen.domain.entity.SysApp;
import org.mapstruct.Mapper;

import com.kecoyo.turtleopen.domain.entity.SysUser;
import com.kecoyo.turtleopen.domain.dto.CategoryDTO;

@Mapper(componentModel = "spring")
public interface StructMapper {

    SysApp toCategory(CategoryDTO dto);

    CategoryDTO toCategoryDto(SysApp sysApp);

    SysUser toAccount(UserDto dto);

    UserDto toAccountDto(SysUser sysUser);

}
