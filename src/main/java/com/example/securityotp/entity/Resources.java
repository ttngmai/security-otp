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
public class Resources implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String httpMethod;
    private String type;
    private int orderNum;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resources")
    private Set<RoleResource> roleResources = new HashSet<>();

    @Builder
    public Resources(String name, String httpMethod, String type, int orderNum) {
        this.name = name;
        this.httpMethod = httpMethod;
        this.type = type;
        this.orderNum = orderNum;
    }
}
