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
public class AuthoritiesDto {
    private String url;
    private Set<Role> roleSet;
}
