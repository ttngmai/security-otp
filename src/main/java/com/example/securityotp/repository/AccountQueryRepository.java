package com.example.securityotp.repository;

import com.example.securityotp.entity.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static com.example.securityotp.entity.QAccount.*;
import static com.example.securityotp.entity.QAccountRole.*;
import static com.example.securityotp.entity.QRole.*;

@RequiredArgsConstructor
@Repository
public class AccountQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Transactional
    public Account findByUsername(String username) {
        return queryFactory
                .selectFrom(account)
                .where(account.username.eq(username))
                .fetchOne();
    }

    @Transactional
    public void findAccountWithRoles() {
        List<Account> accounts = queryFactory
                .selectFrom(account)
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
                .fetch();

        List<Long> ids = new ArrayList<>();
        for (Account account : accounts) {
            ids.add(account.getId());
        }

        List<AccountRole> accountRoles = queryFactory
                .selectFrom(accountRole)
                .join(accountRole.account, account).fetchJoin()
                .join(accountRole.role, role).fetchJoin()
                .where(accountRole.account.id.in(ids))
                .fetch();
    }
}
