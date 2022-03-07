package com.example.securityotp.dto;

import com.example.securityotp.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResourcesDto {
    private String id;
    private String name;
    private String httpMethod;
    private int orderNum;
    private String type;
    private String roleName;
    private Set<Role> roleSet;
}
