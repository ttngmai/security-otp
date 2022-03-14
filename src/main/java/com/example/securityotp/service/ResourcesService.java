package com.example.securityotp.service;

import com.example.securityotp.dto.resource.ResourcesDetailDto;
import com.example.securityotp.entity.Resources;
import com.example.securityotp.entity.Role;
import com.example.securityotp.entity.RoleResource;
import com.example.securityotp.mapper.ResourcesMapper;
import com.example.securityotp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ResourcesService {
    private final ResourcesRepository resourcesRepository;
    private final ResourcesQueryRepository resourcesQueryRepository;
    private final RoleRepository roleRepository;
    private final RoleResourceRepository roleResourceRepository;
    private final RoleResourceQueryRepository roleResourceQueryRepository;
    private final ResourcesMapper resourcesMapper;

    @Transactional
    public ResourcesDetailDto getResources(long id) {
        return resourcesQueryRepository.findResourceWithRoles(id);
    }

    @Transactional
    public List<Resources> getResources() {
        return resourcesRepository.findAll(Sort.by(Sort.Order.asc("orderNum")));
    }

    @Transactional
    public void createResources(ResourcesDetailDto resourcesDetailDto){
        Resources resources = resourcesMapper.detailDtoToEntity(resourcesDetailDto);
        List<String> roleNames = resourcesDetailDto.getRoleNames();

        resourcesRepository.save(resources);

        for (String roleName : roleNames) {
            Role role = roleRepository.findByName(roleName);

            RoleResource roleResource = RoleResource.builder()
                    .resources(resources)
                    .role(role)
                    .build();

            roleResourceRepository.save(roleResource);
        }
    }

    @Transactional
    public void updateResources(ResourcesDetailDto resourcesDetailDto) {
        Resources resources = resourcesMapper.detailDtoToEntity(resourcesDetailDto);
        List<String> roleNames = resourcesDetailDto.getRoleNames();
        List<String> ownRoleNames = roleResourceQueryRepository.findRoleNameByResourcesId(resources.getId());

        if (roleNames.size() > 0) {
            Iterator<String> iter = roleNames.iterator();

            while (iter.hasNext()) {
                String roleName = iter.next();

                if (ownRoleNames.contains(roleName)) {
                    iter.remove();
                    ownRoleNames.remove(roleName);
                }
            }

            for (String roleName : roleNames) {
                Role role = roleRepository.findByName(roleName);

                RoleResource roleResource = RoleResource.builder()
                        .resources(resources)
                        .role(role)
                        .build();

                roleResourceRepository.save(roleResource);
            }

            for (String ownRoleName : ownRoleNames) {
                Role role = roleRepository.findByName(ownRoleName);

                roleResourceQueryRepository.deleteByResourcesIdAndRoleId(resources.getId(), role.getId());
            }

            resourcesRepository.save(resources);
        }
    }

    @Transactional
    public void deleteResources(long id) {
        roleResourceQueryRepository.deleteByResourceId(id);
        resourcesRepository.deleteById(id);
    }
}
