package com.example.securityotp.security.service;

import com.example.securityotp.entity.Account;
import com.example.securityotp.repository.AccountQueryRepository;
import com.example.securityotp.repository.AccountRepository;
import com.example.securityotp.security.user.AccountContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private AccountQueryRepository accountQueryRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountQueryRepository.findByUsername(username);

        if (account == null) {
            throw new UsernameNotFoundException("존재하지 않는 사용자 입니다.");
        }

        List<String> accountRoles = account.getAccountRoles()
                .stream()
                .map(accountRole -> accountRole.getRole().getName())
                .collect(Collectors.toList());

        List<SimpleGrantedAuthority> roles = accountRoles
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        roles.add(new SimpleGrantedAuthority("ROLE_PRE_VERIFICATION"));

        AccountContext accountContext = new AccountContext(account, roles);

        return accountContext;
    }
}
