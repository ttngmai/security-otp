package com.example.securityotp.dto.account;

import com.example.securityotp.dto.RoleDto;

import java.time.LocalDateTime;
import java.util.Set;

public class AccountDetailDto {
    private String username;
    private String password;
    private String secretKey;
    private int loginFailCount;
    private int otpFailCount;
    private boolean isAccountNonLocked;
    private LocalDateTime lastLogin;
    private Set<RoleDto> roles;
}
