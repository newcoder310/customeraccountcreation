package com.api.accounts.repository;

import com.api.accounts.model.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AccountDAO {

    private final AccountRepository accountRepository;

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public List<Account> retrieveAccountsForACustomer(Long customerID) {
        return accountRepository.findByCustomerId(customerID);
    }

}
