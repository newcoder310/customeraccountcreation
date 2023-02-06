package com.api.accounts.service;

import com.api.accounts.model.AccountBalance;
import com.api.accounts.model.AccountRequestBody;
import com.api.accounts.model.AccountResponseBody;

public interface AccountService {

    public AccountResponseBody createAccount(AccountRequestBody accountRequest);

    AccountBalance retrieveAccountBalanceByCustomerId(Long customerId);

}
