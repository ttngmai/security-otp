package com.example.securityotp.controller;

import com.example.securityotp.dto.ResourcesDto;
import com.example.securityotp.entity.Resources;
import com.example.securityotp.entity.Role;
import com.example.securityotp.repository.RoleRepository;
import com.example.securityotp.security.metadatasource.UrlSecurityMetadataSource;
import com.example.securityotp.service.ResourcesService;
import com.example.securityotp.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Controller
public class ResourcesController {
    private final ResourcesService resourcesService;
    private final RoleService roleService;
    private final RoleRepository roleRepository;
    private final RoleQueryRepository roleQueryRepository;
    private final UrlSecurityMetadataSource urlSecurityMetadataSource;

    @GetMapping(value="/admin/resources")
    public String getResources(Model model) throws Exception {

        List<Resources> resources = resourcesService.getResources();
        model.addAttribute("resources", resources);

        return "admin/resource/list";
    }

    @PostMapping(value="/admin/resources")
    public String createResources(ResourcesDto resourcesDto) throws Exception {

        Role role = roleQueryRepository.findByRoleName(resourcesDto.getRoleName());
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        Resources resources = modelMapper.map(resourcesDto, Resources.class);
        resources.setRoleSet(roles);

        resourcesService.createResources(resources);

        if("url".equals(resourcesDto.getType())){
            urlSecurityMetadataSource.reload();
        }

        return "redirect:/admin/resources";
    }
}
