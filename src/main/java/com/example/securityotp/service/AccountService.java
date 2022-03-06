package com.example.securityotp.service;

import com.example.securityotp.entity.Account;
import com.example.securityotp.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Transactional
    public void createAccount(Account account) {
        accountRepository.save(account);
    }
}
