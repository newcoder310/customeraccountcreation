package com.api.accounts.controller;

import com.api.accounts.model.AccountBalance;
import com.api.accounts.model.AccountRequestBody;
import com.api.accounts.model.AccountResponseBody;
import com.api.accounts.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@Validated
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/account/createCurrentAccount")
    public ResponseEntity<AccountResponseBody> createCurrentAccount(@Valid @RequestBody AccountRequestBody accountRequestBody) {
        return new ResponseEntity<>(accountService.createAccount(accountRequestBody), HttpStatus.OK);
    }

    @GetMapping("/account/retrieveAccountBalanceByCustomerId/{customerId}")
    public ResponseEntity<AccountBalance> retrieveAccountBalanceByCustomerId(@PathVariable("customerId") Long customerId) {
        return new ResponseEntity<>(accountService.retrieveAccountBalanceByCustomerId(customerId), HttpStatus.OK);
    }
}
