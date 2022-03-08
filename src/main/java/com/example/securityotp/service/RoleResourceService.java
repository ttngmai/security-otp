package com.example.securityotp.service;

import com.example.securityotp.entity.Resources;
import com.example.securityotp.entity.Role;
import com.example.securityotp.entity.RoleResource;
import com.example.securityotp.repository.RoleResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class RoleResourceService {
    private final RoleResourceRepository roleResourceRepository;

    @Transactional
    public void createRoleResource(Role role, Resources resources) {
        RoleResource roleResource = new RoleResource(role, resources);

        roleResourceRepository.save(roleResource);
    }
}
