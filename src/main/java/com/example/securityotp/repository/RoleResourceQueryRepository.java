package com.example.securityotp.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.securityotp.entity.QRole.role;
import static com.example.securityotp.entity.QRoleResource.roleResource;

@RequiredArgsConstructor
@Repository
public class RoleResourceQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<String> findRoleNameByResourcesId(long id) {
        return queryFactory
                .select(role.name)
                .from(roleResource)
                .join(roleResource.role, role)
                .where(roleResource.resources.id.eq(id))
                .fetch();
    }

    public void deleteByResourceId(long id) {
        queryFactory
                .delete(roleResource)
                .where(roleResource.resources.id.eq(id))
                .execute();
    }

    public void deleteByResourcesIdAndRoleId(long resourcesId, long roleId) {
        queryFactory
                .delete(roleResource)
                .where(
                        roleResource.resources.id.eq(resourcesId),
                        roleResource.role.id.eq(roleId)
                )
                .execute();
    }
}
