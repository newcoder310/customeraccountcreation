package com.api.accounts.mappers;

import com.api.accounts.model.Account;
import com.api.accounts.model.TransactionRequestBody;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionRequestBodyMapperTest {

    private TransactionRequestBodyMapper mapper = Mappers.getMapper(TransactionRequestBodyMapper.class);

    @Test
    public void testMapToTransactionRequestBody() {
        Account source = new Account();
        source.setBalance(new BigDecimal(100));
        source.setCustomerId(12345l);
        TransactionRequestBody target = mapper.mapToTransactionRequestBody(source);

        assertEquals(new BigDecimal(100), new BigDecimal(target.getAmount()));
        assertEquals(12345l, target.getCustomerId());
    }
}