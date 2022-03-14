package com.example.securityotp.repository;

import com.example.securityotp.dto.resource.ResourcesDetailDto;
import com.example.securityotp.entity.QResources;
import com.example.securityotp.entity.QRole;
import com.example.securityotp.entity.Resources;
import com.example.securityotp.entity.RoleResource;
import com.example.securityotp.mapper.ResourcesMapper;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.securityotp.entity.QResources.resources;
import static com.example.securityotp.entity.QRole.role;
import static com.example.securityotp.entity.QRoleResource.roleResource;

@RequiredArgsConstructor
@Repository
public class ResourcesQueryRepository {
    private final JPAQueryFactory queryFactory;
    private final ResourcesMapper resourcesMapper;

    public List<RoleResource> findAllRoleResource() {

        return queryFactory
                .selectFrom(roleResource)
                .join(roleResource.role, role).fetchJoin()
                .join(roleResource.resources, resources).fetchJoin()
                .orderBy(resources.orderNum.asc())
                .fetch();
    }

    public ResourcesDetailDto findResourceWithRoles(long id) {
        Resources resource = queryFactory
                .selectFrom(QResources.resources)
                .where(QResources.resources.id.eq(id))
                .fetchOne();

        if (resource == null) {
            return null;
        }

        List<RoleResource> roleResources = queryFactory
                .selectFrom(roleResource)
                .join(roleResource.resources, resources).fetchJoin()
                .join(roleResource.role, role).fetchJoin()
                .where(roleResource.resources.id.in(resource.getId()))
                .fetch();

        List<String> roleNames = roleResources
                .stream()
                .map(r -> r.getRole().getName())
                .collect(Collectors.toList());

        return resourcesMapper.entityToDetailDto(resource, roleNames);
    }
}
