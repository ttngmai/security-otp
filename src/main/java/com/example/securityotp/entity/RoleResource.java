package com.example.securityotp.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ROLE_RESOURCE")
@SequenceGenerator(name = "ROLE_RESOURCE_SEQ_GEN", sequenceName = "ROLE_RESOURCE_SEQ", initialValue = 1, allocationSize = 1)
public class RoleResource implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROLE_RESOURCE_SEQ_GEN")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_id")
    private Resources resources;

    @Builder
    public RoleResource(Role role, Resources resources) {
        this.role = role;
        this.resources = resources;
    }
}
