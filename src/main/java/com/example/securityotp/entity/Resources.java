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
@Table(name = "RESOURCES")
public class Resources {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String httpMethod;
    private int orderNum;
    private String type;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resources")
    private Set<RoleResource> roleResources = new HashSet<>();

    public Resources(String name, String httpMethod, int orderNum, String type) {
        this.name = name;
        this.httpMethod = httpMethod;
        this.orderNum = orderNum;
        this.type = type;
    }
}
