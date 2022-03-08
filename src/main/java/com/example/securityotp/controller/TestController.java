package com.example.securityotp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
    @GetMapping("/user/one")
    public String userPageOne() {
        return "user/one";
    }

    @GetMapping("/user/two")
    public String userPageTwo() {
        return "user/two";
    }

    @GetMapping("/user/three")
    public String userPageThree() {
        return "user/three";
    }

    @GetMapping("/manager/one")
    public String managerPageOne() {
        return "manager/one";
    }

    @GetMapping("/manager/two")
    public String managerPageTwo() {
        return "manager/two";
    }

    @GetMapping("/manager/three")
    public String managerPageThree() {
        return "manager/three";
    }
}
