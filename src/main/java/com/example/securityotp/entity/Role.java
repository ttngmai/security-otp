package com.example.securityotp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ROLE")
public class Role {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
    private Set<AccountRole> accountRoles = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
    private Set<RoleResource> roleResources = new HashSet<>();
}
