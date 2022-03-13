package com.example.securityotp.mapper;

import com.example.securityotp.dto.ResourcesDto;
import com.example.securityotp.entity.Resources;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ResourcesMapper {
    @Mapping(target = "roleName", ignore = true)
    ResourcesDto entityToDto(Resources resources);

    Resources dtoToEntity(ResourcesDto resourcesDto);
}
