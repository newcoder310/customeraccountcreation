package com.api.accounts.service.impl;

import com.api.accounts.client.RestClient;
import com.api.accounts.exception.ExternalCallFailedException;
import com.api.accounts.model.CustomerResponseBody;
import com.api.accounts.model.TransactionRequestBody;
import com.api.accounts.service.RestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestServiceImpl implements RestService {

    private final RestClient restClient;

    @Override
    public void callTransactionService(TransactionRequestBody transactionRequestBody) {
        try {
            restClient.callTransactionService(transactionRequestBody);
        } catch (Exception exception) {
            throw new ExternalCallFailedException("The external call to transactions service failed");
        }
    }

    @Override
    public CustomerResponseBody callCustomerService(Long customerId) {
        try {
            return restClient.callCustomerService(customerId);
        } catch (Exception exception) {
            throw new ExternalCallFailedException("The external call to customer service failed");
        }
    }
}
