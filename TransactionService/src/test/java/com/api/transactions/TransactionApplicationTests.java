package com.api.transactions;


import com.api.transactions.model.Transaction;
import com.api.transactions.model.TransactionRequestBody;
import com.api.transactions.repository.TransactionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class TransactionApplicationTests {

    private final static String URI = "/transaction/createCurrentAccount";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TransactionRepository transactionRepository;

    @BeforeEach
    public void init() {
        Transaction transaction = new Transaction();
        transaction.setDate(LocalDateTime.now());
        transaction.setAmount(new BigDecimal("10.0"));
        transaction.setAccountId(99234l);
        transaction.setCustomerId(123456l);
        when(transactionRepository.findByCustomerId(123456l)).thenReturn(Arrays.asList(transaction));
    }

    @Test
    void testAccountCreation_expect_200() throws Exception {
        TransactionRequestBody requestBody = new TransactionRequestBody();
        requestBody.setAccountId(1234l);
        requestBody.setAmount("10.0");
        requestBody.setCustomerId(123456l);
        mockMvc.perform(post("/transaction/createTransaction").contentType("application/json")
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk());
    }

    @Test
    void testAccountCreationMissingAccountId_expect_400() throws Exception {
        TransactionRequestBody requestBody = new TransactionRequestBody();
        requestBody.setAmount("10.0");
        requestBody.setCustomerId(123456l);
        mockMvc.perform(post("/transaction/createTransaction").contentType("application/json")
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Request Validation Failed")));
    }

    @Test
    void testAccountCreationMissingAmount_expect_400() throws Exception {
        TransactionRequestBody requestBody = new TransactionRequestBody();
        requestBody.setAccountId(1234l);
        requestBody.setCustomerId(123456l);
        mockMvc.perform(post("/transaction/createTransaction").contentType("application/json")
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Request Validation Failed")));
    }

    @Test
    void testAccountCreationMissingCustomerId_expect_400() throws Exception {
        TransactionRequestBody requestBody = new TransactionRequestBody();
        requestBody.setAmount("10.0");
        requestBody.setCustomerId(123456l);
        mockMvc.perform(post("/transaction/createTransaction").contentType("application/json")
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Request Validation Failed")));
    }

    @Test
    void testRetrieveAccounts() throws Exception {
        mockMvc.perform(get("/transaction/retrieveTransactionsByCustomerId/123456")
                .contentType("application/json")).andExpect(jsonPath("$.transactions", hasSize(1)));
    }

}
