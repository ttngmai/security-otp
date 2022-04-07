package com.example.securityotp.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "MENU")
@SequenceGenerator(name = "MENU_SEQ_GEN", sequenceName = "MENU_SEQ", initialValue = 1, allocationSize = 1)
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MENU_SEQ_GEN")
    private Long id;

    private String name;
    private String url;
    private int level;
    private int orderNum;
    private boolean useYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Menu parent;
//
//    @OneToMany(mappedBy = "parent")
//    private List<Menu> children = new ArrayList<>();
}
