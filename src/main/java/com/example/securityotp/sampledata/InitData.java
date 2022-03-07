package com.example.securityotp.sampledata;

import com.example.securityotp.entity.Resources;
import com.example.securityotp.entity.Role;
import com.example.securityotp.entity.RoleResource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class InitData {
    private final InitDataService initDataService;

    @PostConstruct
    public void init() {
        initDataService.init();
    }

    @Component
    static class InitDataService {
        @PersistenceContext
        EntityManager em;

        @Transactional
        public void init() {
            Role rolePreVerification = new Role("ROLE_PRE_VERIFICATION");
            Role rolePostVerification = new Role("ROLE_POST_VERIFICATION");

            em.persist(rolePreVerification);
            em.persist(rolePostVerification);

            Resources resources1 = new Resources("/auth/otp", "get", 2, "url");
            Resources resources2 = new Resources("/", "get", 1, "url");
            Resources resources3 = new Resources("/auth/logout", "get", 3, "url");

            em.persist(resources1);
            em.persist(resources2);
            em.persist(resources3);

            RoleResource roleResource1 = new RoleResource();
            roleResource1.setRole(rolePreVerification);
            roleResource1.setResources(resources1);

            RoleResource roleResource2 = new RoleResource();
            roleResource2.setRole(rolePostVerification);
            roleResource2.setResources(resources2);

            RoleResource roleResource3 = new RoleResource();
            roleResource3.setRole(rolePostVerification);
            roleResource3.setResources(resources3);

            em.persist(roleResource1);
            em.persist(roleResource2);
            em.persist(roleResource3);
        }
    }
}
