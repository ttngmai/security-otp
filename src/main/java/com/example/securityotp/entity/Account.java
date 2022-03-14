package com.example.securityotp.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ACCOUNT")
public class Account implements Serializable {
    private static final int LOGIN_RETRY_LIMIT = 5;
    private static final int OTP_RETRY_LIMIT = 5;

    @Id
    @GeneratedValue
    private Long id;

    private String username;
    private String password;
    private String secretKey;
    private int loginFailCount;
    private int otpFailCount;
    private boolean isAccountNonLocked;
    private LocalDateTime lastLogin;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
    private Set<AccountRole> accountRoles = new HashSet<>();

    @Builder
    public Account(Long id, String username, String password, String secretKey, int loginFailCount, int otpFailCount, boolean isAccountNonLocked, LocalDateTime lastLogin) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.secretKey = secretKey;
        this.loginFailCount = loginFailCount;
        this.otpFailCount = otpFailCount;
        this.isAccountNonLocked = isAccountNonLocked;
        this.lastLogin = lastLogin;
    }

    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    public void loginFail() {
        this.loginFailCount += 1;

        if (this.loginFailCount >= LOGIN_RETRY_LIMIT) {
            this.accountLock();
        }
    }

    public void otpFail() {
        this.otpFailCount += 1;

        if (this.otpFailCount >= OTP_RETRY_LIMIT) {
            this.accountLock();
        }
    }

    public int loginRetryRemaining() {
        return LOGIN_RETRY_LIMIT - this.loginFailCount;
    }

    public int otpRetryRemaining() {
        return OTP_RETRY_LIMIT - this.otpFailCount;
    }

    public void loginSuccess() {
        this.resetLoginFailCount();
    }

    public void otpSuccess(LocalDateTime successTime) {
        this.resetOtpCount();
        this.lastLogin = successTime;
    }

    private void accountLock() {
        this.isAccountNonLocked = false;
    }

    private void resetLoginFailCount() {
        this.loginFailCount = 0;
    }

    private void resetOtpCount() {
        this.otpFailCount = 0;
    }
}
