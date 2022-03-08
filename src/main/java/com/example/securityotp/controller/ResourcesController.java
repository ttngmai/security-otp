package com.example.securityotp.controller;

import com.example.securityotp.dto.ResourcesDto;
import com.example.securityotp.entity.Resources;
import com.example.securityotp.entity.Role;
import com.example.securityotp.mapper.ResourcesMapper;
import com.example.securityotp.repository.RoleQueryRepository;
import com.example.securityotp.repository.RoleRepository;
import com.example.securityotp.security.metadatasource.UrlSecurityMetadataSource;
import com.example.securityotp.service.ResourcesService;
import com.example.securityotp.service.RoleResourceService;
import com.example.securityotp.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ResourcesController {
    private final ResourcesService resourcesService;
    private final RoleService roleService;
    private final RoleRepository roleRepository;
    private final RoleQueryRepository roleQueryRepository;
    private final RoleResourceService roleResourceService;
    private final ResourcesMapper resourcesMapper;
    private final UrlSecurityMetadataSource urlSecurityMetadataSource;

    @GetMapping(value="/admin/resources")
    public String getResources(Model model) throws Exception {
        List<Resources> resources = resourcesService.getResources();
        model.addAttribute("resources", resources);

        return "admin/resource/list";
    }

    @PostMapping(value="/admin/resources")
    public String createResources(ResourcesDto resourcesDto) throws Exception {
        Resources resources = resourcesMapper.dtoToEntity(resourcesDto);
        Role role = roleQueryRepository.findByRoleName(resourcesDto.getRoleName());

        resourcesService.createResources(resources);
        roleResourceService.createRoleResource(role, resources);

        if("url".equals(resourcesDto.getType())){
            urlSecurityMetadataSource.init();
        }

        return "redirect:/admin/resources";
    }

    @GetMapping("/admin/resources/register")
    public String viewRoles(Model model) throws Exception {
        List<Role> roleList = roleService.getRoles();
        ResourcesDto resources = new ResourcesDto();

        model.addAttribute("roleList", roleList);
        model.addAttribute("resources", resources);

        return "admin/resource/detail";
    }

    @GetMapping("/admin/resources/{id}")
    public String getResources(@PathVariable String id, Model model) throws Exception {
        List<Role> roleList = roleService.getRoles();
        Resources resources = resourcesService.getResources(Long.valueOf(id));
        ResourcesDto resourcesDto = resourcesMapper.entityToDto(resources);

        model.addAttribute("resources", resourcesDto);
        model.addAttribute("roleList", roleList);

        return "admin/resource/detail";
    }

    @GetMapping(value = "/admin/resources/delete/{id}")
    public String removeResources(@PathVariable String id, Model model) throws Exception {
        Resources resources = resourcesService.getResources(Long.valueOf(id));
        resourcesService.deleteResources(Long.valueOf(id));

        if ("url".equals(resources.getType())) {
            urlSecurityMetadataSource.init();
        }

        return "redirect:/admin/resources";
    }
}
