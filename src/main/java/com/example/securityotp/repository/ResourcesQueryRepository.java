package com.example.securityotp.repository;

import com.example.securityotp.dto.AuthoritiesDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.securityotp.entity.QResources.resources;
import static com.example.securityotp.entity.QRole.role;
import static com.example.securityotp.entity.QRoleResource.roleResource;

@RequiredArgsConstructor
@Repository
public class ResourcesQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<AuthoritiesDto> findAllAuthorities() {
        List<AuthoritiesDto> authorities = queryFactory
                .select(Projections.fields(
                        AuthoritiesDto.class, resources.name.as("url"), role.name.as("roleName"))
                )
                .from(role)
                .join(role.roleResources, roleResource)
                .where(role.eq(roleResource.role))
                .join(roleResource.resources, resources)
                .where(roleResource.resources.eq(resources)).fetch();

        return authorities;
    }
}
