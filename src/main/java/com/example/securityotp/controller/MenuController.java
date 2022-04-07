package com.example.securityotp.controller;

import com.example.securityotp.dto.MenuDto;
import com.example.securityotp.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/menu")
public class MenuController {
    private final MenuService menuService;

//    @ResponseBody
//    @GetMapping
//    public List<MenuDto> menu() {
//        return menuService.findAllMenu();
//    }
}
