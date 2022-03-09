package com.example.securityotp.init;

import com.example.securityotp.entity.Post;
import com.example.securityotp.entity.Resources;
import com.example.securityotp.entity.Role;
import com.example.securityotp.entity.RoleResource;
import com.example.securityotp.repository.PostRepository;
import com.example.securityotp.repository.ResourcesRepository;
import com.example.securityotp.repository.RoleRepository;
import com.example.securityotp.repository.RoleResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

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
        private final ResourcesRepository resourcesRepository;
        private final RoleResourceRepository roleResourceRepository;
        private final PostRepository postRepository;

        @Transactional
        public void init() {
            Role rolePreVerification = createRoleIfNotFound("ROLE_PRE_VERIFICATION");
            Role rolePostVerification = createRoleIfNotFound("ROLE_POST_VERIFICATION");

            Resources resource1 = createResourceIfNotFound("/auth/otp", "GET", "url", 1);
            Resources resource2 = createResourceIfNotFound("/", "GET", "url", 1);
            Resources resource3 = createResourceIfNotFound("/auth/logout", "GET", "url", 1);

            if (roleResourceRepository.findAll().size() == 0) {
                createRoleResourceIfNotFound(rolePreVerification, resource1);
                createRoleResourceIfNotFound(rolePostVerification, resource2);
                createRoleResourceIfNotFound(rolePostVerification, resource3);
            }

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

        public void createRoleResourceIfNotFound(Role role, Resources resources) {
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
    }
}
