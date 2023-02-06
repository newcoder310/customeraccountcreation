package com.api.accounts.client;

import com.api.accounts.client.config.RestClientConfig;
import com.api.accounts.model.CustomerResponseBody;
import com.api.accounts.model.TransactionRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class RestClient {

    private final RestClientConfig restClientConfig;

    public void callTransactionService(TransactionRequestBody transactionRequestBody) {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.postForObject(restClientConfig.getTransactionServiceHostPort()+"/transaction/createTransaction", transactionRequestBody, TransactionRequestBody.class);
    }

    public CustomerResponseBody callCustomerService(Long customerId) {
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject(restClientConfig.getCustomerServiceHostPort()+"/customer/" + customerId + "/getCustomerInformation", CustomerResponseBody.class);
    }
}
