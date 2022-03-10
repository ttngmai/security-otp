package com.example.securityotp.service;

import com.example.securityotp.entity.Account;
import com.example.securityotp.repository.AccountQueryRepository;
import com.example.securityotp.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountQueryRepository accountQueryRepository;

    @Transactional
    public void createAccount(Account account) {
        accountRepository.save(account);
    }

    @Transactional
    public List<Account> getAccounts() {
        accountQueryRepository.findAccountWithRoles();

        return accountRepository.findAll();
    }
}
