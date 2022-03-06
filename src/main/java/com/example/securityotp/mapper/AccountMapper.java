package com.example.securityotp.mapper;

import com.example.securityotp.dto.AccountDto;
import com.example.securityotp.entity.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    Account dtoToEntity(AccountDto accountDto);
}
