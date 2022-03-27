package com.example.securityotp.init;

import com.example.securityotp.entity.*;
import com.example.securityotp.otp.OtpTokenGenerator;
import com.example.securityotp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SampleDataLoad {
    private final InitDataService initDataService;

    @PostConstruct
    public void init() {
        initDataService.init();
    }

    @Component
    @RequiredArgsConstructor
    static class InitDataService {
        @PersistenceContext
        private EntityManager em;

        private final RoleRepository roleRepository;
        private final AccountRepository accountRepository;
        private final AccountRoleRepository accountRoleRepository;
        private final ResourcesRepository resourcesRepository;
        private final RoleResourceRepository roleResourceRepository;
        private final MenuRepository menuRepository;
        private final PostRepository postRepository;
        private final PasswordEncoder passwordEncoder;
        private final OtpTokenGenerator otpTokenGenerator;

        @Transactional
        public void init() {
            Role rolePreVerification = createRoleIfNotFound("ROLE_PRE_VERIFICATION");
            Role rolePostVerification = createRoleIfNotFound("ROLE_POST_VERIFICATION");
            Role roleUser = createRoleIfNotFound("ROLE_USER");
            Role roleManager = createRoleIfNotFound("ROLE_MANAGER");
            Role roleAdmin = createRoleIfNotFound("ROLE_ADMIN");

            Account account1 = createAccountIfNotFound("user", "1234");
            Account account2 = createAccountIfNotFound("manager", "1234");
            Account account3 = createAccountIfNotFound("admin", "1234");

            if (accountRoleRepository.findAll().size() == 0) {
                createAccountRole(account1, roleUser);

                createAccountRole(account2, roleUser);
                createAccountRole(account2, roleManager);

                createAccountRole(account3, roleUser);
                createAccountRole(account3, roleManager);
                createAccountRole(account3, roleAdmin);
            }

            Resources resource1 = createResourceIfNotFound("/auth/otp", "GET", "url", 1);
            Resources resource2 = createResourceIfNotFound("/auth/logout", "GET", "url", 1);

            if (roleResourceRepository.findAll().size() == 0) {
                createRoleResource(rolePreVerification, resource1);
                createRoleResource(rolePostVerification, resource2);
            }

            createMenusIfNotFound();
            createPostsIfNotFound();
        }

        @Transactional
        public Role createRoleIfNotFound(String name) {
            Role role = roleRepository.findByName(name);

            if (role == null) {
                role = Role.builder()
                        .name(name)
                        .build();
            }

            return roleRepository.save(role);
        }

        @Transactional
        private Account createAccountIfNotFound(String username, String password) {
            Account account = accountRepository.findByUsername(username);

            if (account == null) {
                String secretKey = otpTokenGenerator.generateSecretKey();

                account = Account.builder()
                        .username(username)
                        .password(passwordEncoder.encode(password))
                        .secretKey(secretKey)
                        .isAccountNonLocked(true)
                        .build();

                String imgPath = otpTokenGenerator.generateQRCode(secretKey, account.getUsername());

                System.out.println("imgPath = " + imgPath);
            }

            return accountRepository.save(account);
        }

        @Transactional
        private void createAccountRole(Account account, Role role) {
            AccountRole accountRole = new AccountRole();

            accountRole.setAccount(account);
            accountRole.setRole(role);

            em.persist(accountRole);
        }

        @Transactional
        public Resources createResourceIfNotFound(String name, String httpMethod, String type, int orderNum) {
            Resources resources = resourcesRepository.findByNameAndHttpMethod(name, httpMethod);

            if (resources == null) {
                resources = Resources.builder()
                        .name(name)
                        .httpMethod(httpMethod)
                        .type(type)
                        .orderNum(orderNum)
                        .build();
            }

            return resourcesRepository.save(resources);
        }

        @Transactional
        public void createRoleResource(Role role, Resources resources) {
            RoleResource roleResource = new RoleResource();
            roleResource.setRole(role);
            roleResource.setResources(resources);

            em.persist(roleResource);
        }

        @Transactional
        public void createPostsIfNotFound() {
            if (postRepository.findAll().size() == 0) {
                for (int i = 1; i < 51; i++) {
                    Post post = Post.builder()
                            .title("게시글 " + i)
                            .content("내용 " + i)
                            .build();

                    postRepository.save(post);
                }
            }
        }

        @Transactional
        public void createMenusIfNotFound() {
            if (menuRepository.findAll().size() == 0) {
                for (int i = 1; i < 4; i++) {
                    Menu rootMenu = Menu.builder()
                            .name("루트메뉴" + i)
                            .url("/menu/" + i)
                            .parent(null)
                            .level(1)
                            .orderNum(i)
                            .build();

                    menuRepository.save(rootMenu);
                }

                List<Menu> menus = menuRepository.findAll();

                for (int i = 1; i < 4; i++) {
                    for (int j = 1; j < 4; j++) {
                        Menu childMenu = Menu.builder()
                                .name("자식메뉴" + i + "-" + j)
                                .url("/menu/" + i + "/" + j)
                                .parent(menus.get(i - 1))
                                .level(2)
                                .orderNum(j)
                                .build();

                        menuRepository.save(childMenu);
                    }
                }
            }
        }
    }
}
