package com.kecoyo.turtleopen.service.mapstruct;

import com.kecoyo.turtleopen.domain.dto.UserDTO;
import com.kecoyo.turtleopen.domain.entity.SysApp;
import org.mapstruct.Mapper;

import com.kecoyo.turtleopen.domain.entity.SysUser;
import com.kecoyo.turtleopen.domain.dto.CategoryDTO;

@Mapper(componentModel = "spring")
public interface StructMapper {

    SysApp toCategory(CategoryDTO dto);

    CategoryDTO toCategoryDto(SysApp sysApp);

    SysUser toAccount(UserDTO dto);

    UserDTO toAccountDto(SysUser sysUser);

}
