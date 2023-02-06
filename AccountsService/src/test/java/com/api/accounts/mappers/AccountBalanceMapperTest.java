package com.api.accounts.mappers;

import com.api.accounts.model.AccountBalance;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountBalanceMapperTest {

    private AccountBalanceMapper mapper = Mappers.getMapper(AccountBalanceMapper.class);

    @Test
    public void testMapToAccountBalance() {
        BigDecimal source = new BigDecimal(100);
        AccountBalance target = mapper.mapToAccountBalance(source);

        assertEquals(new BigDecimal(100), new BigDecimal(target.getBalance()));
    }
}
