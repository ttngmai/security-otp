package com.example.securityotp.service;

import com.example.securityotp.dto.account.AccountDetailDto;
import com.example.securityotp.entity.Account;
import com.example.securityotp.entity.AccountRole;
import com.example.securityotp.entity.Role;
import com.example.securityotp.mapper.AccountMapper;
import com.example.securityotp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountQueryRepository accountQueryRepository;
    private final RoleRepository roleRepository;
    private final AccountRoleRepository accountRoleRepository;
    private final AccountRoleQueryRepository accountRoleQueryRepository;
    private final AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createAccount(Account account) {
        accountRepository.save(account);
    }

    @Transactional
    public List<AccountDetailDto> getAccounts() {
        return accountQueryRepository.findAccountsWithRoles();
    }

    @Transactional
    public AccountDetailDto getAccount(long id) {
        return accountQueryRepository.findAccountWithRoles(id);
    }

    @Transactional
    public void updateAccount(AccountDetailDto accountDetailDto) {
        Account account = accountMapper.detailDtoToEntity(accountDetailDto);
        List<String> roleNames = accountDetailDto.getRoleNames();
        List<String> ownRoleNames = accountRoleQueryRepository.findRoleNameByUserId(account.getId());

        if (roleNames.size() > 0) {
            Iterator<String> iter = roleNames.iterator();

            while (iter.hasNext()) {
                String roleName = iter.next();

                if (ownRoleNames.contains(roleName)) {
                    iter.remove();
                    ownRoleNames.remove(roleName);
                }
            }

            for (String roleName : roleNames) {
                Role role = roleRepository.findByName(roleName);

                AccountRole accountRole = AccountRole.builder()
                        .account(account)
                        .role(role)
                        .build();

                accountRoleRepository.save(accountRole);
            }
        }

        for (String ownRoleName : ownRoleNames) {
            Role role = roleRepository.findByName(ownRoleName);

            accountRoleQueryRepository.deleteByAccountIdAndRoleId(account.getId(), role.getId());
        }

        account.setPassword(passwordEncoder.encode(accountDetailDto.getPassword()));
        accountRepository.save(account);
    }
}
