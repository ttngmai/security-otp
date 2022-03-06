package com.example.securityotp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountDto {
    private String username;
    private String password;
    private String secretKey;
    private int loginFailCount;
    private int otpFailCount;
    private boolean isAccountNonLocked;
    private LocalDateTime lastLogin;
}
