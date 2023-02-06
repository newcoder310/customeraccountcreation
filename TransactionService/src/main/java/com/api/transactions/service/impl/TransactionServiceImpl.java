package com.api.transactions.service.impl;


import com.api.transactions.mappers.TransactionMapper;
import com.api.transactions.model.TransactionRequestBody;
import com.api.transactions.model.TransactionResponse;
import com.api.transactions.model.TransactionResponseBody;
import com.api.transactions.repository.TransactionDAO;
import com.api.transactions.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionDAO transactionDAO;

    private final TransactionMapper transactionMapper;

    @Override
    public TransactionResponseBody retrieveTransactionsByCustomerId(Long customerId) {
        //TODO: Find a better way to map it
        List<TransactionResponse> transactionResponses = transactionMapper.mapToTransactionResponseList(transactionDAO.retrieveTransactionsByCustomerId(customerId));
        TransactionResponseBody transactionResponseBody = new TransactionResponseBody();
        transactionResponseBody.setTransactions(transactionResponses);
        return transactionResponseBody;
    }

    public void createTransaction(TransactionRequestBody transactionRequestBody) {
        transactionDAO.createTransaction(transactionMapper.mapToTransactionModel(transactionRequestBody));
    }
}
