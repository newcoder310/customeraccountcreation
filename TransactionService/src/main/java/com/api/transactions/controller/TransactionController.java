package com.api.transactions.controller;


import com.api.transactions.model.TransactionRequestBody;
import com.api.transactions.model.TransactionResponseBody;
import com.api.transactions.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/transaction/createTransaction")
    public void createTransaction(@Valid @RequestBody TransactionRequestBody transactionRequestBody) {
        transactionService.createTransaction(transactionRequestBody);
    }

    @GetMapping("/transaction/retrieveTransactionsByCustomerId/{customerId}")
    public ResponseEntity<TransactionResponseBody> retrieveTransactionsByCustomerId(@PathVariable("customerId") Long customerId) {
        return new ResponseEntity<>(transactionService.retrieveTransactionsByCustomerId(customerId), HttpStatus.OK);
    }
}
