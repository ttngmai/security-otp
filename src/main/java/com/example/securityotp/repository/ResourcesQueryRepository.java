package com.example.securityotp.repository;

import com.example.securityotp.dto.AuthoritiesDto;
import com.example.securityotp.entity.*;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static com.example.securityotp.entity.QResources.resources;
import static com.example.securityotp.entity.QRole.role;
import static com.example.securityotp.entity.QRoleResource.roleResource;

@RequiredArgsConstructor
@Repository
public class ResourcesQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<RoleResource> findAllRoleResource() {
//        List<AuthoritiesDto> authorities = queryFactory
//                .select(Projections.fields(
//                        AuthoritiesDto.class, resources.name.as("url"), role.name.as("roleName"))
//                )
//                .from(role)
//                .join(role.roleResources, roleResource)
//                .where(role.eq(roleResource.role))
//                .join(roleResource.resources, resources)
//                .where(roleResource.resources.eq(resources)).fetch();

        List<RoleResource> roleResources = queryFactory
                .selectFrom(roleResource)
                .join(roleResource.role, role).fetchJoin()
                .join(roleResource.resources, resources).fetchJoin()
                .fetch();

        LinkedHashMap<Resources, List<Role>> map = new LinkedHashMap<>();

        for (RoleResource roleResource : roleResources) {
            Resources resources = roleResource.getResources();

            if (map.containsKey(resources)) {
                List<Role> roles = map.get(resources);

                roles.add(roleResource.getRole());
                map.replace(resources, roles);
            } else {
                List<Role> roles = new ArrayList<>();

                roles.add(roleResource.getRole());
                map.put(resources, roles);
            }
        }

        return roleResources;
    }
}
