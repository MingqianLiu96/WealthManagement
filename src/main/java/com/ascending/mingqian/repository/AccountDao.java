package com.ascending.mingqian.repository;

import com.ascending.mingqian.model.Account;

import java.util.List;

public interface AccountDao {
    boolean save(Account account);
    boolean update(Account account);
    boolean delete(Long id);
    List<Account> getAccounts();
    Account getAccountById(Long id);
    List<Account> getAccountByCustomerId(Long customerId);
}
