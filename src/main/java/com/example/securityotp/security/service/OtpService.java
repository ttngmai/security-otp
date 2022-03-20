package com.example.securityotp.security.service;

import com.example.securityotp.dto.OtpResultDto;
import com.example.securityotp.entity.Account;
import com.example.securityotp.otp.OtpTokenValidator;
import com.example.securityotp.repository.AccountQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class OtpService {
    private final AccountQueryRepository accountQueryRepository;
    private final OtpTokenValidator otpTokenValidator;

    @Transactional
    public OtpResultDto verify(String inputOtpCode, Authentication authentication) {
        Account loginAccount = (Account)authentication.getPrincipal();
        String username = loginAccount.getUsername();
        Account account = accountQueryRepository.findByUsername(username);
        LocalDateTime now = LocalDateTime.now();

        if (account == null) {
            throw new UsernameNotFoundException("존재하지 않는 사용자입니다.");
        }

        if (otpTokenValidator.validate(inputOtpCode, account.getSecretKey())) {
            List<String> accountRoles = account.getAccountRoles()
                    .stream()
                    .map(accountRole -> accountRole.getRole().getName())
                    .collect(Collectors.toList());

            List<SimpleGrantedAuthority> roles = accountRoles
                    .stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            roles.add(new SimpleGrantedAuthority("ROLE_POST_VERIFICATION"));

            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), roles);

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            account.otpSuccess(now);
            log.info(username + " OTP success at " + now.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

            return new OtpResultDto(true, false, "");
        } else {
            account.otpFail();

            String message = "OTP 코드가 올바르지 않습니다. 남은 횟수: " + account.otpRetryRemaining();

            log.info(username + " OTP fail at " + now.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + " : " + message);

            if (!account.isAccountNonLocked()) {
                return new OtpResultDto(false, true, message);
            }

            return new OtpResultDto(false, false, message);
        }
    }
}
