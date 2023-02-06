package com.api.accounts.mappers;

import com.api.accounts.model.Account;
import com.api.accounts.model.AccountResponseBody;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountResponseMapper {

    AccountResponseBody toAccountResponse(Account source);
}
