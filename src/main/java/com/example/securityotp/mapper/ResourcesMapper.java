package com.example.securityotp.mapper;

import com.example.securityotp.dto.ResourcesDto;
import com.example.securityotp.entity.Resources;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResourcesMapper {
    ResourcesDto entityToDto(Resources resources);

    Resources dtoToEntity(ResourcesDto resourcesDto);
}
