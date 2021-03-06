package com.example.securityotp.controller;

import com.example.securityotp.dto.AccountDto;
import com.example.securityotp.dto.OtpResultDto;
import com.example.securityotp.entity.Account;
import com.example.securityotp.mapper.AccountMapper;
import com.example.securityotp.otp.OtpTokenGenerator;
import com.example.securityotp.security.service.OtpService;
import com.example.securityotp.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/auth")
public class AuthController {
    private final AccountService accountService;
    private final OtpService otpService;
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
        account.setAccountNonLocked(true);
        String imgPath = otpTokenGenerator.generateQRCode(secretKey, account.getUsername());

        System.out.println("imgPath = " + imgPath);

        accountService.createAccount(account);

        return "redirect:/auth/login";
    }

    @RequestMapping(value = "/login", method = { RequestMethod.GET, RequestMethod.POST })
    public String login(Model model) {
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

            log.info("OTP ?????? ?????? = {}", e.getMessage());

            return "auth/otp";
        }
    }

    @GetMapping("/denied")
    public String accessDenied(@RequestParam(value = "error", required = false) String error, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = (Account) authentication.getPrincipal();

        model.addAttribute("username", account.getUsername());
        model.addAttribute("error", error);

        return "auth/denied";
    }

    @GetMapping("/logout")
    public String logout() {
        return "auth/logout";
    }
}
