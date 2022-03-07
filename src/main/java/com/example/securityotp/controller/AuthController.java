package com.example.securityotp.controller;

import com.example.securityotp.dto.AccountDto;
import com.example.securityotp.dto.OtpResultDto;
import com.example.securityotp.entity.Account;
import com.example.securityotp.mapper.AccountMapper;
import com.example.securityotp.otp.OtpTokenGenerator;
import com.example.securityotp.repository.AccountRepository;
import com.example.securityotp.security.service.OtpService;
import com.example.securityotp.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/auth")
public class AuthController {
    private final AccountService accountService;
    private final OtpService otpService;
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;
    private final OtpTokenGenerator otpTokenGenerator;

    @GetMapping("/register")
    public String register() {
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerProc(AccountDto accountDto) {
        Account account = accountMapper.dtoToEntity(accountDto);
        String secretKey = otpTokenGenerator.generateSecretKey();

        account.setSecretKey(secretKey);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        String imgPath = otpTokenGenerator.generateQRCode(secretKey, account.getUsername());

        System.out.println("imgPath = " + imgPath);

        accountService.createAccount(account);

        return "redirect:/auth/login";
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/otp")
    public String otp() {
        return "auth/otp";
    }

    @PostMapping("/otp-proc")
    public String otpLogin(String otpCode, Authentication authentication, Model model) {
        try {
            OtpResultDto result = otpService.verify(otpCode, authentication);

            if (result.isSuccess()) {
                return "redirect:/";
            }

            if (result.isAccountLocked()) {
                return "redirect:/auth/login";
            }

            model.addAttribute("globalError", result.getMessage());

            return "auth/otp";
        } catch (Exception e) {
            model.addAttribute("globalError", e.getMessage());

            log.info("OTP 인증 실패 = {}", e.getMessage());

            return "auth/otp";
        }
    }

    @GetMapping("/logout")
    public String logout() {
        return "auth/logout";
    }
}
