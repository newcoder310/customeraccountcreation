package com.api.accounts.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import com.api.accounts.mappers.AccountBalanceMapper;
import com.api.accounts.mappers.AccountRequestMapper;
import com.api.accounts.mappers.AccountResponseMapper;
import com.api.accounts.mappers.TransactionRequestBodyMapper;
import com.api.accounts.model.*;
import com.api.accounts.repository.AccountDAO;
import com.api.accounts.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {

    @Mock
    private AccountDAO accountDAO;

    @Mock
    private AccountRequestMapper accountRequestMapper;

    @Mock
    private AccountBalanceMapper accountBalanceMapper;

    @Mock
    private AccountResponseMapper accountResponseMapper;

    @Mock
    private TransactionRequestBodyMapper transactionRequestBodyMapper;

    @Mock
    private RestService restService;

    private AccountServiceImpl accountService;

    @BeforeEach
    public void setUp() {
        accountService = new AccountServiceImpl(accountDAO, accountRequestMapper, accountBalanceMapper,
                accountResponseMapper, transactionRequestBodyMapper, restService);
    }

    @Test
    public void testCreateAccount() {
        AccountRequestBody accountRequest = new AccountRequestBody();
        Account account = new Account();
        account.setCustomerId(1234l);
        account.setAccountId(1l);
        account.setBalance(BigDecimal.ZERO);
        when(accountRequestMapper.toAccount(accountRequest)).thenReturn(account);
        when(accountDAO.createAccount(any(Account.class))).thenReturn(account);
        when(accountResponseMapper.toAccountResponse(any(Account.class))).thenReturn(new AccountResponseBody());
        when(transactionRequestBodyMapper.mapToTransactionRequestBody(any(Account.class))).thenReturn(new TransactionRequestBody());
        when(restService.callCustomerService(any(Long.class))).thenReturn(new CustomerResponseBody().customerId(1234l));

        AccountResponseBody result = accountService.createAccount(accountRequest);

        assertNotNull(result);
    }

    @Test
    public void testRetrieveAccountBalanceByCustomerId() {
        BigDecimal balance = new BigDecimal("1000");
        Account account = new Account();
        account.setBalance(balance);
        when(accountDAO.retrieveAccountsForACustomer(any(Long.class))).thenReturn(List.of(account));
        when(accountBalanceMapper.mapToAccountBalance(any(BigDecimal.class))).thenReturn(new AccountBalance());

        AccountBalance result = accountService.retrieveAccountBalanceByCustomerId(1L);

        assertNotNull(result);
    }

}