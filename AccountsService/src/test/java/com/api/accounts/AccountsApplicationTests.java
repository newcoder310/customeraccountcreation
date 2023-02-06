package com.api.accounts;

import com.api.accounts.client.RestClient;
import com.api.accounts.exception.ExternalCallFailedException;
import com.api.accounts.model.Account;
import com.api.accounts.model.AccountRequestBody;
import com.api.accounts.model.CustomerResponseBody;
import com.api.accounts.repository.AccountDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AccountsApplicationTests {

    public static final Long CUSTOMER_ID = 123456l;
    public static final Long ACCOUNT_ID = 456743l;
    public static final String INITIAL_CREDIT = "10.0";
    public static final String ZERO_INITIAL_CREDIT = "0.0";
    public static final String NEGATIVE_INITIAL_CREDIT = "-1.0";
    public static final String CREATE_CURRENT_ACCOUNT = "/account/createCurrentAccount";
    public static final String RETRIEVE_ACCOUNT_BALANCE = "/account/retrieveAccountBalanceByCustomerId/";
    public static final String APPLICATION_JSON = "application/json";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RestClient restClient;

    @MockBean
    private AccountDAO accountDAO;

    @Test
    void testAccountCreationWithZeroBalance() throws Exception {

        Account account = new Account();
        account.setCustomerId(CUSTOMER_ID);
        account.setBalance(new BigDecimal("0.0"));
        when(accountDAO.createAccount(account)).thenReturn(account);

        CustomerResponseBody customerResponseBody = new CustomerResponseBody();
        customerResponseBody.setCustomerId(CUSTOMER_ID);
        customerResponseBody.setName("John");
        customerResponseBody.setName("Doe");
        when(restClient.callCustomerService(CUSTOMER_ID)).thenReturn(customerResponseBody);

        AccountRequestBody requestBody = new AccountRequestBody();
        requestBody.setCustomerId(CUSTOMER_ID);
        requestBody.initialCredit(ZERO_INITIAL_CREDIT);
        callAccountCreate(requestBody).andExpect(jsonPath("$.balance", is("0.0")));

        verify(accountDAO).createAccount(any());
        verify(restClient, never()).callTransactionService(any());
    }

    @Test
    void testAccountCreationWithPositiveBalance() throws Exception {
        Account account = new Account();
        account.setCustomerId(CUSTOMER_ID);
        account.setBalance(new BigDecimal("10.0"));
        when(accountDAO.createAccount(account)).thenReturn(account);
        CustomerResponseBody customerResponseBody = new CustomerResponseBody();
        customerResponseBody.setCustomerId(CUSTOMER_ID);
        customerResponseBody.setName("John");
        customerResponseBody.setName("Doe");
        when(restClient.callCustomerService(CUSTOMER_ID)).thenReturn(customerResponseBody);

        AccountRequestBody requestBody = new AccountRequestBody();
        requestBody.setCustomerId(CUSTOMER_ID);
        requestBody.initialCredit(INITIAL_CREDIT);
        callAccountCreate(requestBody).andExpect(jsonPath("$.balance", is("10.0")));

        verify(accountDAO).createAccount(any());
        verify(restClient, times(1)).callTransactionService(any());
    }

    @Test
    void testAccountCreationWithNegativeBalance() throws Exception {
        AccountRequestBody requestBody = new AccountRequestBody();
        requestBody.setCustomerId(CUSTOMER_ID);
        requestBody.initialCredit(NEGATIVE_INITIAL_CREDIT);

        CustomerResponseBody customerResponseBody = new CustomerResponseBody();
        customerResponseBody.setCustomerId(CUSTOMER_ID);
        customerResponseBody.setName("John");
        customerResponseBody.setName("Doe");
        when(restClient.callCustomerService(CUSTOMER_ID)).thenReturn(customerResponseBody);

        callAccountCreate(requestBody);

        verify(accountDAO).createAccount(any());
        verify(restClient, never()).callTransactionService(any());
    }


    @Test
    void testRetrieveAccountBalanceByCustomerId() throws Exception {
        Account account1 = new Account();
        account1.setAccountId(ACCOUNT_ID);
        account1.setCustomerId(CUSTOMER_ID);
        account1.setBalance(new BigDecimal("10.0"));
        Account account2 = new Account();
        account2.setAccountId(ACCOUNT_ID);
        account2.setCustomerId(CUSTOMER_ID);
        account2.setBalance(new BigDecimal("10.0"));
        when(accountDAO.retrieveAccountsForACustomer(CUSTOMER_ID)).thenReturn(Arrays.asList(account1, account2));


        mockMvc.perform(get(RETRIEVE_ACCOUNT_BALANCE + CUSTOMER_ID).contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.balance", is("20.0")));


        verify(accountDAO, times(1)).retrieveAccountsForACustomer(any());
    }

    @Test
    void testAccountCreationWithCallFailedException() throws Exception {

        Account account = new Account();
        account.setCustomerId(CUSTOMER_ID);
        account.setBalance(new BigDecimal("0.0"));
        when(accountDAO.createAccount(account)).thenReturn(account);

        CustomerResponseBody customerResponseBody = new CustomerResponseBody();
        customerResponseBody.setCustomerId(CUSTOMER_ID);
        customerResponseBody.setName("John");
        customerResponseBody.setName("Doe");
        when(restClient.callCustomerService(CUSTOMER_ID)).thenThrow(new ExternalCallFailedException("No client present"));

        AccountRequestBody requestBody = new AccountRequestBody();
        requestBody.setCustomerId(CUSTOMER_ID);
        requestBody.initialCredit(ZERO_INITIAL_CREDIT);
        mockMvc.perform(post(CREATE_CURRENT_ACCOUNT).contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message", is("The external call to customer service failed")));

    }


    @Test
    void testAccountCreationWithEmptyCustomerResponse() throws Exception {

        Account account = new Account();
        account.setCustomerId(CUSTOMER_ID);
        account.setBalance(new BigDecimal("0.0"));
        when(accountDAO.createAccount(account)).thenReturn(account);


        when(restClient.callCustomerService(CUSTOMER_ID)).thenReturn(null);

        AccountRequestBody requestBody = new AccountRequestBody();
        requestBody.setCustomerId(CUSTOMER_ID);
        requestBody.initialCredit(ZERO_INITIAL_CREDIT);
        mockMvc.perform(post(CREATE_CURRENT_ACCOUNT).contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Invalid customer")));

    }

    @Test
    void testAccountCreationWithMissingCustomerId_expect_400() throws Exception {
        AccountRequestBody requestBody = new AccountRequestBody();
        requestBody.initialCredit(ZERO_INITIAL_CREDIT);
        mockMvc.perform(post(CREATE_CURRENT_ACCOUNT).contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Request Validation Failed")));
    }


    @Test
    void testAccountCreationWithMissingInitialCredit_expect_400() throws Exception {
        AccountRequestBody requestBody = new AccountRequestBody();
        requestBody.setCustomerId(CUSTOMER_ID);
        mockMvc.perform(post(CREATE_CURRENT_ACCOUNT).contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Request Validation Failed")));
    }

    @Test
    void testAccountCreationWithInvalidFormatOfInitalCredit_expect_400() throws Exception {
        AccountRequestBody requestBody = new AccountRequestBody();
        requestBody.setCustomerId(CUSTOMER_ID);
        requestBody.initialCredit("bad format");
        mockMvc.perform(post(CREATE_CURRENT_ACCOUNT).contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Request Validation Failed")));
    }




    private ResultActions callAccountCreate(AccountRequestBody requestBody) throws Exception {
        return mockMvc.perform(post(CREATE_CURRENT_ACCOUNT).contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk());
    }

}
