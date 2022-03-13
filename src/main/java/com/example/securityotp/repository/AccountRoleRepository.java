package com.example.securityotp.repository;

import com.example.securityotp.entity.AccountRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRoleRepository extends JpaRepository<AccountRole, Long> {
}
