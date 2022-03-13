package com.example.securityotp.controller;

import com.example.securityotp.dto.account.AccountDetailDto;
import com.example.securityotp.entity.Role;
import com.example.securityotp.service.AccountService;
import com.example.securityotp.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AccountController {
    private final AccountService accountService;
    private final RoleService roleService;

    @GetMapping("/admin/accounts")
    public String getAccounts(Model model) {
        List<AccountDetailDto> accounts = accountService.getAccounts();

        model.addAttribute("accounts", accounts);

        return "admin/account/list";
    }

    @PostMapping("/admin/accounts")
    public String updateAccount(AccountDetailDto accountDetailDto) {
        log.info("roleNames={}", accountDetailDto.getRoleNames());

        accountService.updateAccount(accountDetailDto);

        return "redirect:/admin/accounts";
    }

    @GetMapping("/admin/accounts/{id}")
    public String getAccount(@PathVariable("id") Long id, Model model) {
        AccountDetailDto account = accountService.getAccount(id);
        List<Role> roles = roleService.getRoles();

        model.addAttribute("account", account);
        model.addAttribute("roleList", roles);

        return "admin/account/detail";
    }
}
