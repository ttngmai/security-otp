package com.example.securityotp.dto;

import lombok.Getter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@Getter
public class MenuDto {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String url;
    private int level;
    private int orderNum;
    private List<MenuDto> children;

    public MenuDto(Long id, String name, String url, int level, int orderNum, List<MenuDto> children) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.level = level;
        this.orderNum = orderNum;
        this.children = children;
    }
}
