package com.example.securityotp.security.metadatasource;

import com.example.securityotp.dto.AuthoritiesDto;
import com.example.securityotp.security.envent.AuthoritiesManager;
import com.example.securityotp.security.service.ResourceMetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UrlSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    private ResourceMetaService resourceMetaService;

    @Autowired
    private AuthoritiesManager authoritiesManager;

    @PostConstruct
    public void init() throws Exception {
        resourceMetaService.findAllResources();
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        String url = ((FilterInvocation) object).getRequestUrl();

        List<AuthoritiesDto> authoritiesDtos = authoritiesManager.getAuthorities().get(url);

        if (authoritiesDtos == null) {
            return null;
        }

        List<String> roles = authoritiesDtos.stream().map(AuthoritiesDto::getRoleName).collect(Collectors.toList());

        String[] stockArr = new String[roles.size()];
        stockArr = roles.toArray(stockArr);

        return SecurityConfig.createList(stockArr);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
