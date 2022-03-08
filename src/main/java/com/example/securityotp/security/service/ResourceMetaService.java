package com.example.securityotp.security.service;

import com.example.securityotp.dto.AuthoritiesDto;
import com.example.securityotp.entity.RoleResource;
import com.example.securityotp.repository.ResourcesQueryRepository;
import com.example.securityotp.sampledata.InitData;
import com.example.securityotp.security.envent.AuthoritiesEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.ConfigAttribute;
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
    InitData initData;

    public void findAllResources() {
        LinkedHashMap<RequestMatcher, List<ConfigAttribute>> result = new LinkedHashMap<>();
        List<RoleResource> roleResources = resourcesQueryRepository.findAllRoleResource();

//        authorities.stream().forEach(authoritiesDto -> {
//            log.info("role name: {}", authoritiesDto.getRoleName());
//            log.info("url: {}", authoritiesDto.getUrl());
//        });

        List<AuthoritiesDto> authorities = new ArrayList<>();

        applicationContext.publishEvent(new AuthoritiesEvent(this, authorities));
    }
}
