package com.api.accounts.service;

import com.api.accounts.model.CustomerResponseBody;
import com.api.accounts.model.TransactionRequestBody;

public interface RestService {

    void callTransactionService(TransactionRequestBody transactionRequestBody);

    CustomerResponseBody callCustomerService(Long customerId);
}
