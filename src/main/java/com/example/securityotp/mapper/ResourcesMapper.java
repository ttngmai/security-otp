package com.example.securityotp.mapper;

import com.example.securityotp.dto.ResourcesDto;
import com.example.securityotp.dto.resource.ResourcesDetailDto;
import com.example.securityotp.entity.Resources;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ResourcesMapper {
    @Mapping(target = "roleName", ignore = true)
    ResourcesDto entityToDto(Resources resources);
    ResourcesDetailDto entityToDetailDto(Resources resources, List<String> roleNames);

    Resources dtoToEntity(ResourcesDto resourcesDto);

    Resources detailDtoToEntity(ResourcesDetailDto resourcesDetailDto);
}
