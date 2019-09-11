package com.ascending.mingqian.service;

import com.ascending.mingqian.model.Account;
import com.ascending.mingqian.repository.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountDao accountDao;

    public boolean save(Account account){
        return accountDao.save(account);
    }
    public boolean update(Account account){
        return accountDao.update(account);
    }
    public boolean delete(Long id){
        return accountDao.delete(id);
    }
    public List<Account> getAccounts(){
        return accountDao.getAccounts();
    }
    public Account getAccountById(Long id){
        return accountDao.getAccountById(id);
    }
    public List<Account> getAccountByCustomerId(Long customerId){
        return accountDao.getAccountByCustomerId(customerId);
    }
}
