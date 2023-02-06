package com.api.transactions.service;


import com.api.transactions.model.TransactionRequestBody;
import com.api.transactions.model.TransactionResponseBody;

public interface TransactionService {
    TransactionResponseBody retrieveTransactionsByCustomerId(Long customerId);

    void createTransaction(TransactionRequestBody transactionRequestBody);
}
