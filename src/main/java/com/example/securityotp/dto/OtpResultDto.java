package com.example.securityotp.dto;

import lombok.Getter;

@Getter
public class OtpResultDto {
    private boolean isSuccess;
    private boolean isAccountLocked;
    private String message;

    public OtpResultDto(boolean isSuccess, boolean isAccountLocked, String message) {
        this.isSuccess = isSuccess;
        this.isAccountLocked = isAccountLocked;
        this.message = message;
    }
}
