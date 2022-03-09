package com.example.securityotp.security.envent;

import org.springframework.context.ApplicationListener;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;

@Component
public class AuthoritiesManager implements ApplicationListener<AuthoritiesEvent> {
    private LinkedHashMap<RequestMatcher, List<ConfigAttribute>> authorities;

    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getAuthorities() {
        return authorities;
    }

    @Override
    public void onApplicationEvent(AuthoritiesEvent event) {
        authorities = event.getAuthoritiesDto();
    }
}
