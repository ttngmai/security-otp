package com.example.securityotp.mapper;

import com.example.securityotp.dto.PostDto;
import com.example.securityotp.entity.Post;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {
    Post dtoToEntity(PostDto menuDto);

    List<Post> dtosToEntities(List<PostDto> menuDtos);

    PostDto entityToDto(Post menu);

    List<PostDto> entitiesToDtos(List<Post> menus);
}
