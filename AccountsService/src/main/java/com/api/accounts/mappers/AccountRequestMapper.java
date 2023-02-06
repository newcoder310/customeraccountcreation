package com.api.accounts.mappers;

import com.api.accounts.model.Account;
import com.api.accounts.model.AccountRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountRequestMapper {

    @Mapping(target = "balance", source = "initialCredit")
    Account toAccount(AccountRequestBody accountRequestBody);
}
