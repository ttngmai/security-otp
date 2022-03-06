package com.example.securityotp.repository;

import com.example.securityotp.entity.Resources;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resources, Long> {
}
