package com.example.securityotp.repository;

import com.example.securityotp.entity.Account;
import com.example.securityotp.entity.QAccount;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Repository
public class AccountQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Transactional
    public Account findByUsername(String username) {
        Account account = queryFactory
                .selectFrom(QAccount.account)
                .where(QAccount.account.username.eq(username))
                .fetchOne();

        return account;
    }
}
