package com.api.transactions.repository;


import com.api.transactions.model.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TransactionDAO {

    private final TransactionRepository transactionRepository;

    public void createTransaction(Transaction transaction) {
        transaction.setDate(LocalDateTime.now());
        transactionRepository.save(transaction);
    }

    public List<Transaction> retrieveTransactionsByCustomerId(Long customerId) {
        return transactionRepository.findByCustomerId(customerId);
    }
}
