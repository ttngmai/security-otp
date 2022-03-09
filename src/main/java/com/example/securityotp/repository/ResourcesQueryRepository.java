package com.example.securityotp.repository;

import com.example.securityotp.entity.RoleResource;
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

    public List<RoleResource> findAllRoleResource() {
        List<RoleResource> roleResources = queryFactory
                .selectFrom(roleResource)
                .join(roleResource.role, role).fetchJoin()
                .join(roleResource.resources, resources).fetchJoin()
                .orderBy(resources.orderNum.asc())
                .fetch();

        return roleResources;
    }
}
