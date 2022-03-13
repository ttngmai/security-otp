package com.example.securityotp.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ROLE")
public class Role implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
    private Set<AccountRole> accountRoles = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
    private Set<RoleResource> roleResources = new HashSet<>();

    @Builder
    public Role(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
