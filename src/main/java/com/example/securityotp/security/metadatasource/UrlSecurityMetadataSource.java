package com.example.securityotp.security.metadatasource;

import com.example.securityotp.security.envent.AuthoritiesManager;
import com.example.securityotp.security.service.ResourceMetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UrlSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    private ResourceMetaService resourceMetaService;

    @Autowired
    private AuthoritiesManager authoritiesManager;

    @PostConstruct
    public void reload() {
        resourceMetaService.findAllResources();
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        HttpServletRequest request = ((FilterInvocation) object).getRequest();
        LinkedHashMap<RequestMatcher, List<ConfigAttribute>> requestMap = authoritiesManager.getAuthorities();

        if (requestMap != null) {
            for (Map.Entry<RequestMatcher, List<ConfigAttribute>> entry : requestMap.entrySet()) {
                RequestMatcher matcher = entry.getKey();
                if (matcher.matches(request)) {
                    throw new AccessDeniedException("위험한 URL 미리 차단!");
//                    return entry.getValue();
                }
            }
        }

        return null;
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
