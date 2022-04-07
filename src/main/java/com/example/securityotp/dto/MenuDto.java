package com.example.securityotp.dto;

import com.example.securityotp.entity.Menu;
import com.example.securityotp.util.NestedConvertHelper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MenuDto {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String url;
    private int level;
    private int orderNum;
    private boolean useYn;
    private List<MenuDto> children;

    public static List<MenuDto> toDtoList(List<Menu> menus) {
        NestedConvertHelper helper = NestedConvertHelper.newInstance(
                menus,
                c -> new MenuDto(
                        c.getId(), c.getName(), c.getUrl(),
                        c.getLevel(), c.getOrderNum(), c.isUseYn(),
                        new ArrayList<>()),
                c -> c.getParent(),
                c -> c.getId(),
                d -> d.getChildren());

        return helper.convert();
    }
}
