package com.example.securityotp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class homeController {
    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/user")
    public String pre() {
        return "user";
    }
}
