package com.example.securityotp.mapper;

import com.example.securityotp.dto.AccountDto;
import com.example.securityotp.dto.RoleDto;
import com.example.securityotp.dto.account.AccountDetailDto;
import com.example.securityotp.entity.Account;
import com.example.securityotp.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    @Mapping(source = "accountNonLocked", target = "isAccountNonLocked")
    Account dtoToEntity(AccountDto accountDto);

    @Mapping(source = "accountNonLocked", target = "isAccountNonLocked")
    Account detailDtoToEntity(AccountDetailDto accountDetailDto);

    AccountDto entityToDto(Account account);

    AccountDetailDto entityToDetailDto(Account account, List<String> roleNames);
}
