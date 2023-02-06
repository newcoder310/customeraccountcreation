package com.api.accounts.mappers;

import com.api.accounts.model.Account;
import com.api.accounts.model.TransactionRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransactionRequestBodyMapper {

    @Mapping(target = "amount", source = "balance")
    TransactionRequestBody mapToTransactionRequestBody(Account account);
}
