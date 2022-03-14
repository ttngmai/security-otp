package com.example.securityotp.repository;

import com.example.securityotp.dto.account.AccountDetailDto;
import com.example.securityotp.entity.Account;
import com.example.securityotp.entity.AccountRole;
import com.example.securityotp.entity.QAccount;
import com.example.securityotp.mapper.AccountMapper;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.securityotp.entity.QAccount.account;
import static com.example.securityotp.entity.QAccountRole.accountRole;
import static com.example.securityotp.entity.QRole.role;

@RequiredArgsConstructor
@Repository
public class AccountQueryRepository {
    private final JPAQueryFactory queryFactory;
    private final AccountMapper accountMapper;

    @Transactional
    public Account findByUsername(String username) {
        return queryFactory
                .selectFrom(account)
                .where(account.username.eq(username))
                .fetchOne();
    }

    @Transactional
    public List<AccountDetailDto> findAccountsWithRoles() {
        List<Account> accounts = queryFactory
                .selectFrom(account)
                .fetch();

        if (accounts == null) {
            return null;
        }

        List<Long> ids = accounts
                .stream()
                .map(Account::getId)
                .collect(Collectors.toList());

        List<AccountRole> accountRoles = queryFactory
                .selectFrom(accountRole)
                .join(accountRole.account, account).fetchJoin()
                .join(accountRole.role, role).fetchJoin()
                .where(accountRole.account.id.in(ids))
                .fetch();

        LinkedHashMap<Account, List<String>> map = new LinkedHashMap<>();

        for (Account a : accounts) {
            List<String> roleNames = new ArrayList<>();

            map.put(a, roleNames);
        }

        for (AccountRole accountRole : accountRoles) {
            Account account = accountRole.getAccount();
            List<String> roleNames = map.get(account);

            roleNames.add(accountRole.getRole().getName());
            map.replace(account, roleNames);
        }

        List<AccountDetailDto> accountDetailDtos = new ArrayList<>();

        for (Map.Entry<Account, List<String>> entry : map.entrySet()) {
            Account account = entry.getKey();
            List<String> roleNames = entry.getValue();

            accountDetailDtos.add(accountMapper.entityToDetailDto(account, roleNames));
        }

        return accountDetailDtos;
    }

    @Transactional
    public AccountDetailDto findAccountWithRoles(Long id) {
        Account account = queryFactory
                .selectFrom(QAccount.account)
                .where(QAccount.account.id.eq(id))
                .fetchOne();

        if (account == null) {
            return null;
        }

        List<AccountRole> accountRoles = queryFactory
                .selectFrom(accountRole)
                .join(accountRole.account, QAccount.account).fetchJoin()
                .join(accountRole.role, role).fetchJoin()
                .where(accountRole.account.id.in(account.getId()))
                .fetch();

        List<String> roleNames = accountRoles
                .stream()
                .map(r -> r.getRole().getName())
                .collect(Collectors.toList());

        return accountMapper.entityToDetailDto(account, roleNames);
    }
}
