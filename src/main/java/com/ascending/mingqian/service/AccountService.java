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

    boolean save(Account account){
        return accountDao.save(account);
    }
    boolean update(Account account){
        return accountDao.update(account);
    }
    boolean delete(Long id){
        return accountDao.delete(id);
    }
    List<Account> getAccounts(){
        return accountDao.getAccounts();
    }
    Account getAccountById(Long id){
        return accountDao.getAccountById(id);
    }
    List<Account> getAccountByUserId(Long userId){
        return accountDao.getAccountByUserId(userId);
    }
}
