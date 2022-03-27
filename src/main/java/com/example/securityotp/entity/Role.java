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
@SequenceGenerator(name = "ROLE_SEQ_GEN", sequenceName = "ROLE_SEQ", initialValue = 1, allocationSize = 1)
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROLE_SEQ_GEN")
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
