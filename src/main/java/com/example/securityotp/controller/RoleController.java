package com.example.securityotp.controller;

import com.example.securityotp.dto.RoleDto;
import com.example.securityotp.entity.Role;
import com.example.securityotp.mapper.RoleMapper;
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
public class RoleController {
    private final RoleService roleService;
    private final RoleMapper roleMapper;

    @GetMapping(value="/admin/roles")
    public String getRoles(Model model) throws Exception {

        List<Role> roles = roleService.getRoles();
        model.addAttribute("roles", roles);

        return "admin/role/list";
    }

    @GetMapping(value="/admin/roles/register")
    public String viewRoles(Model model) throws Exception {

        RoleDto role = new RoleDto();
        model.addAttribute("role", role);

        return "admin/role/detail";
    }

    @PostMapping("/admin/roles")
    public String createRole(RoleDto roleDto) throws Exception {
        Role role = roleMapper.dtoToEntity(roleDto);
        roleService.createRole(role);

        return "redirect:/admin/roles";
    }

    @GetMapping("/admin/roles/{id}")
    public String getRole(@PathVariable String id, Model model) throws Exception{
        Role role = roleService.getRole(Long.valueOf(id));

        RoleDto roleDto = roleMapper.entityToDto(role);
        model.addAttribute("role", roleDto);

        return "admin/role/detail";
    }

    @GetMapping("/admin/roles/delete/{id}")
    public String removeResources(@PathVariable String id, Model model) {
        Role role = roleService.getRole(Long.valueOf(id));

        roleService.deleteRole(Long.valueOf(id));

        return "redirect:/admin/resources";
    }
}
