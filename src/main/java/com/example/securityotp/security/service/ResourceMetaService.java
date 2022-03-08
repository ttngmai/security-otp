package com.example.securityotp.security.service;

import com.example.securityotp.dto.AuthoritiesDto;
import com.example.securityotp.repository.ResourcesQueryRepository;
import com.example.securityotp.sampledata.InitData;
import com.example.securityotp.security.envent.AuthoritiesEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

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
        List<AuthoritiesDto> authorities = resourcesQueryRepository.findAllAuthorities();

        authorities.stream().forEach(authoritiesDto -> {
            log.info("role name: {}", authoritiesDto.getRoleName());
            log.info("url: {}", authoritiesDto.getUrl());
        });

        applicationContext.publishEvent(new AuthoritiesEvent(this, authorities));
    }
}
