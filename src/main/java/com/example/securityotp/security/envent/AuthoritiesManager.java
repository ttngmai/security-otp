package com.example.securityotp.security.envent;

import com.example.securityotp.dto.AuthoritiesDto;
import org.springframework.context.ApplicationListener;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AuthoritiesManager implements ApplicationListener<AuthoritiesEvent> {
    private Map<String, List<AuthoritiesDto>> authorities;

    private Map<String, List<AuthoritiesDto>> getAuthorities() {
        return authorities;
    }

    public List<AuthoritiesDto> getAuthorities(String key) {
        return authorities.get(key);
    }

    @Override
    public void onApplicationEvent(AuthoritiesEvent event) {
        authorities = event.getAuthoritiesDto()
                .stream().collect(Collectors.groupingBy(AuthoritiesDto::getUrl, Collectors.toList()));
    }
}
