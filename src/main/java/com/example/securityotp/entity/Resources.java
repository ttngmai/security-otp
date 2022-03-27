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
@Table(name = "RESOURCES")
@SequenceGenerator(name = "RESOURCES_SEQ_GEN", sequenceName = "RESOURCES_SEQ", initialValue = 1, allocationSize = 1)
public class Resources implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RESOURCES_SEQ_GEN")
    private Long id;

    private String name;
    private String httpMethod;
    private String type;
    private int orderNum;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resources")
    private Set<RoleResource> roleResources = new HashSet<>();

    @Builder
    public Resources(Long id, String name, String httpMethod, String type, int orderNum) {
        this.id = id;
        this.name = name;
        this.httpMethod = httpMethod;
        this.type = type;
        this.orderNum = orderNum;
    }
}
