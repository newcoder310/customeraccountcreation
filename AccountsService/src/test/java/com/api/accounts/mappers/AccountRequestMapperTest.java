package com.api.accounts.mappers;

import com.api.accounts.model.Account;
import com.api.accounts.model.AccountRequestBody;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountRequestMapperTest {

    private AccountRequestMapper mapper = Mappers.getMapper(AccountRequestMapper.class);

    @Test
    public void testToAccount() {
        AccountRequestBody source = new AccountRequestBody();
        source.setInitialCredit(new BigDecimal(100).toString());
        Account target = mapper.toAccount(source);

        assertEquals(new BigDecimal(100), target.getBalance());
    }
}
