package com.api.accounts.service.impl;

import com.api.accounts.exception.CustomerNotPresentException;
import com.api.accounts.mappers.AccountBalanceMapper;
import com.api.accounts.mappers.AccountRequestMapper;
import com.api.accounts.mappers.AccountResponseMapper;
import com.api.accounts.mappers.TransactionRequestBodyMapper;
import com.api.accounts.model.Account;
import com.api.accounts.model.AccountBalance;
import com.api.accounts.model.AccountRequestBody;
import com.api.accounts.model.AccountResponseBody;
import com.api.accounts.model.CustomerResponseBody;
import com.api.accounts.model.TransactionRequestBody;
import com.api.accounts.repository.AccountDAO;
import com.api.accounts.service.AccountService;
import com.api.accounts.service.RestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountDAO accountDAO;

    private final AccountRequestMapper accountRequestMapper;

    private final AccountBalanceMapper accountBalanceMapper;

    private final AccountResponseMapper accountResponseMapper;

    private final TransactionRequestBodyMapper transactionRequestBodyMapper;

    private final RestService restService;

    @Override
    public AccountResponseBody createAccount(AccountRequestBody accountRequest) {
        Account account = mapToAccount(accountRequest);
        return createAccount(account);
    }

    @Override
    public AccountBalance retrieveAccountBalanceByCustomerId(Long customerId) {
        return accountBalanceMapper.mapToAccountBalance(retrieveAccountsByCustomerId(customerId).stream().map(Account::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    private List<Account> retrieveAccountsByCustomerId(Long customerId) {
        return accountDAO.retrieveAccountsForACustomer(customerId);
    }

    private AccountResponseBody createAccount(Account account) {

        CustomerResponseBody customerResponseBody = restService.callCustomerService(account.getCustomerId());

        if (customerResponseBody == null || customerResponseBody.getCustomerId() == null) {
            throw new CustomerNotPresentException("Invalid customer");
        }

        AccountResponseBody accountResponseBody = accountResponseMapper.toAccountResponse(accountDAO.createAccount(account));
        callTransaction(account);

        return accountResponseBody;
    }

    private void callTransaction(Account account) {
        TransactionRequestBody transactionRequestBody = transactionRequestBodyMapper.mapToTransactionRequestBody(account);

        if (isBalanceGreaterThanZero(account)) {
            restService.callTransactionService(transactionRequestBody);
        }
    }

    private static boolean isBalanceGreaterThanZero(Account account) {
        return account.getBalance()!=null && account.getBalance().compareTo(BigDecimal.ZERO) > 0;
    }

    private Account mapToAccount(AccountRequestBody accountRequest) {
        return accountRequestMapper.toAccount(accountRequest);
    }
}
