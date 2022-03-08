package com.example.securityotp.repository;

import com.example.securityotp.entity.QRole;
import com.example.securityotp.entity.Role;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class RoleQueryRepository {
    private final JPAQueryFactory queryFactory;

    public Role findByRoleName(String name) {
        Role role = queryFactory
                .selectFrom(QRole.role)
                .where(QRole.role.name.eq(name))
                .fetchOne();

        return role;
    }
}
