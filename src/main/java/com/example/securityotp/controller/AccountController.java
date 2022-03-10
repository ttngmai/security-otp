package com.example.securityotp.controller;

import com.example.securityotp.entity.Account;
import com.example.securityotp.service.AccountService;
import com.example.securityotp.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class AccountController {
    private final AccountService accountService;
    private final RoleService roleService;

    @GetMapping("/admin/accounts")
    public String getAccounts(Model model) throws Exception {
        List<Account> accounts = accountService.getAccounts();

        model.addAttribute("accounts", accounts);

        return "admin/account/list";
    }
}
