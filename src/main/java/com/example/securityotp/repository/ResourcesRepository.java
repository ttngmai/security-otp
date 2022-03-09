package com.example.securityotp.repository;

import com.example.securityotp.entity.Resources;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourcesRepository extends JpaRepository<Resources, Long> {
    Resources findByNameAndHttpMethod(String name, String httpMethod);
}
