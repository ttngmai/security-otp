package com.example.securityotp.controller;

import com.example.securityotp.dto.resource.ResourcesDetailDto;
import com.example.securityotp.entity.Resources;
import com.example.securityotp.entity.Role;
import com.example.securityotp.security.metadatasource.UrlSecurityMetadataSource;
import com.example.securityotp.service.ResourcesService;
import com.example.securityotp.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ResourcesController {
    private final ResourcesService resourcesService;
    private final RoleService roleService;
    private final UrlSecurityMetadataSource urlSecurityMetadataSource;

    @GetMapping("/admin/resources")
    public String getResources(Model model) {
        List<Resources> resources = resourcesService.getResources();
        model.addAttribute("resources", resources);

        return "admin/resource/list";
    }

    @PostMapping("/admin/resources")
    public String createResources(ResourcesDetailDto resourcesDetailDto) {
        log.info("roleNames={}", resourcesDetailDto.getRoleNames());

        resourcesService.createResources(resourcesDetailDto);

        if("url".equals(resourcesDetailDto.getType())){
            urlSecurityMetadataSource.reload();
        }

        return "redirect:/admin/resources";
    }

    @PostMapping("/admin/resources/update")
    public String updateResources(ResourcesDetailDto resourcesDetailDto) {
        log.info("roleNames={}", resourcesDetailDto.getRoleNames());

        resourcesService.updateResources(resourcesDetailDto);

        if ("url".equals(resourcesDetailDto.getType())) {
            urlSecurityMetadataSource.reload();
        }

        return "redirect:/admin/resources";
    }

    @GetMapping("/admin/resources/register")
    public String viewRoles(Model model) {
        List<Role> roleList = roleService.getRoles();
        ResourcesDetailDto resourcesDetailDto = new ResourcesDetailDto();

        model.addAttribute("roleList", roleList);
        model.addAttribute("resources", resourcesDetailDto);

        return "admin/resource/register";
    }

    @GetMapping("/admin/resources/{id}")
    public String getResources(@PathVariable("id") String id, Model model) {
        ResourcesDetailDto resources = resourcesService.getResources(Long.parseLong(id));
        List<Role> roleList = roleService.getRoles();

        model.addAttribute("resources", resources);
        model.addAttribute("roleList", roleList);

        return "admin/resource/detail";
    }

    @GetMapping(value = "/admin/resources/delete/{id}")
    public String removeResources(@PathVariable("id") String id, Model model) {
        ResourcesDetailDto resources = resourcesService.getResources(Long.parseLong(id));
        resourcesService.deleteResources(Long.parseLong(id));

        if ("url".equals(resources.getType())) {
            urlSecurityMetadataSource.reload();
        }

        return "redirect:/admin/resources";
    }
}
