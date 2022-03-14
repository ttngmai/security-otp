package com.example.securityotp.dto.resource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResourcesDetailDto {
    private Long id;
    private String name;
    private String httpMethod;
    private String type;
    private int orderNum;
    private List<String> roleNames;
}
