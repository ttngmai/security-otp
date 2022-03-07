package com.example.securityotp.mapper;

import com.example.securityotp.dto.RoleDto;
import com.example.securityotp.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role dtoToEntity(RoleDto roleDto);

    RoleDto entityToDto(Role role);
}
