package com.example.securityotp.mapper;

import com.example.securityotp.dto.MenuDto;
import com.example.securityotp.entity.Menu;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MenuMapper {
    Menu dtoToEntity(MenuDto menuDto);

    List<Menu> dtosToEntities(List<MenuDto> menuDtos);

    MenuDto entityToDto(Menu menu);

    List<MenuDto> entitiesToDtos(List<Menu> menus);
}
