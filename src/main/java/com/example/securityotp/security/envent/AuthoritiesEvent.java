package com.example.securityotp.security.envent;

import com.example.securityotp.dto.AuthoritiesDto;
import org.springframework.context.ApplicationEvent;

import java.util.List;

public class AuthoritiesEvent extends ApplicationEvent {
    final List<AuthoritiesDto> authoritiesDto;

    public AuthoritiesEvent(Object source, List<AuthoritiesDto> authoritiesDto) {
        super(source);
        this.authoritiesDto = authoritiesDto;
    }

    public List<AuthoritiesDto> getAuthoritiesDto() {
        return authoritiesDto;
    }
}
