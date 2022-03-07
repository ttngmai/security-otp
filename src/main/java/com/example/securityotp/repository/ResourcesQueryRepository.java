package com.example.securityotp.repository;

import com.example.securityotp.entity.QResources;
import com.example.securityotp.entity.Resources;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.securityotp.entity.QResources.*;

@RequiredArgsConstructor
@Repository
public class ResourcesQueryRepository {
    private final JPAQueryFactory queryFactory;

//    @Query("select r from Resources r join fetch r.roleSet where r.resourceType = 'url' order by r.orderNum desc")
    public List<Resources> findAllResources() {
        List<Resources> resources = queryFactory
                .selectFrom(QResources.resources)
                .join(QResources.resources.roleResources).fetchJoin()
                .where(QResources.resources.type.eq("url"))
                .orderBy(QResources.resources.orderNum.desc())
                .fetch();

        return resources;
    }
}
