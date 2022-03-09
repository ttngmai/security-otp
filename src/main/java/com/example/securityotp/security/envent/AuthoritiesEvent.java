package com.example.securityotp.security.envent;

import org.springframework.context.ApplicationEvent;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.LinkedHashMap;
import java.util.List;

public class AuthoritiesEvent extends ApplicationEvent {
    final LinkedHashMap<RequestMatcher, List<ConfigAttribute>> map;

    public AuthoritiesEvent(Object source, LinkedHashMap<RequestMatcher, List<ConfigAttribute>> map) {
        super(source);
        this.map = map;
    }

    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getAuthoritiesDto() {
        return map;
    }
}
