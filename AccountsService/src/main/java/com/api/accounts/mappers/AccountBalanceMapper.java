package com.api.accounts.mappers;

import com.api.accounts.model.AccountBalance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.math.BigDecimal;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountBalanceMapper {

    @Mapping(target = "balance", source = "source")
    AccountBalance mapToAccountBalance(BigDecimal source);

}
