package com.example.securityotp.service;

import com.example.securityotp.dto.MenuDto;
import com.example.securityotp.entity.Menu;
import com.example.securityotp.entity.QMenu;
import com.example.securityotp.mapper.MenuMapper;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MenuService {
    private final JPAQueryFactory queryFactory;
    private final MenuMapper menuMapper;

//    @Transactional(readOnly = true)
//    public List<MenuDto> findAllMenu() {
//        QMenu parent = new QMenu("parent");
//        QMenu child = new QMenu("child");
//
//        List<Menu> entities = queryFactory
//                .selectFrom(parent)
//                .distinct()
//                .leftJoin(parent.children, child).fetchJoin()
//                .where(
//                        parent.parent.isNull()
//                )
//                .orderBy(parent.orderNum.asc(), child.orderNum.asc())
//                .fetch();
//
//        return menuMapper.entitiesToDtos(entities);
//    }
}
