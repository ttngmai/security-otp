package com.example.securityotp.security.service;

import com.example.securityotp.entity.RoleResource;
import com.example.securityotp.repository.ResourcesQueryRepository;
import com.example.securityotp.init.SampleDataLoad;
import com.example.securityotp.security.envent.AuthoritiesEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Slf4j
@Service
public class ResourceMetaService {
    @Autowired
    private ResourcesQueryRepository resourcesQueryRepository;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    SampleDataLoad sampleDataLoad;

    public void findAllResources() {
        LinkedHashMap<RequestMatcher, List<ConfigAttribute>> map = new LinkedHashMap<>();
        List<RoleResource> roleResources = resourcesQueryRepository.findAllRoleResource();

        for (RoleResource roleResource : roleResources) {
            RequestMatcher requestMatcher = new AntPathRequestMatcher(roleResource.getResources().getName());

            if (map.containsKey(requestMatcher)) {
                List<ConfigAttribute> roles = map.get(requestMatcher);

                roles.add(new SecurityConfig(roleResource.getRole().getName()));
                map.replace(requestMatcher, roles);
            } else {
                List<ConfigAttribute> configAttributes = new ArrayList<>();

                configAttributes.add(new SecurityConfig(roleResource.getRole().getName()));
                map.put(requestMatcher, configAttributes);
            }
        }

        applicationContext.publishEvent(new AuthoritiesEvent(this, map));
    }
}
