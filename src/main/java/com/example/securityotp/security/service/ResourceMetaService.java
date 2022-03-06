package com.example.securityotp.security.service;

import com.example.securityotp.dto.AuthoritiesDto;
import com.example.securityotp.repository.ResourceQueryRepository;
import com.example.securityotp.security.envent.AuthoritiesEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.List;

@Slf4j
public class ResourceMetaService {
    @Autowired
    private ResourceQueryRepository resourceQueryRepository;

    @Autowired
    private ApplicationContext applicationContext;

    public void findAllResources() {
        List<AuthoritiesDto> authorities = resourceQueryRepository.findAllAuthorities();

        authorities.stream().forEach(authoritiesDto -> {
            log.info("role name {} ", authoritiesDto.getRoleName());
            log.info("url {}", authoritiesDto.getUrl());
        });

        applicationContext.publishEvent(new AuthoritiesEvent(this, authorities));
    }
}
