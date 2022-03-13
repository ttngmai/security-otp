package com.example.securityotp.dto.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountDetailDto {
    private Long id;
    private String username;
    private String password;
    private String secretKey;
    private int loginFailCount;
    private int otpFailCount;
    private boolean isAccountNonLocked;
    private LocalDateTime lastLogin;
    private List<String> roleNames;
}
