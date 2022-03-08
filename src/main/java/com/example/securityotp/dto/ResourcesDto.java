package com.example.securityotp.dto;

import com.example.securityotp.entity.RoleResource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResourcesDto {
    private Long id;
    private String name;
    private String httpMethod;
    private String type;
    private int orderNum;
    private String roleName;
    private Set<RoleResource> roleResources = new HashSet<>();
}
