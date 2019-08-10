package com.ascending.mingqian.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "accounts")

public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "balance")
    private double balance;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "user_id")
    private long userId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public long getUsers_id() {
        return userId;
    }

    public void setUsers_id(long users_id) {
        this.userId = users_id;
    }


    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", balance=" + balance +
                ", accountType='" + accountType + '\'' +
                ", userId=" + userId +
                '}';
    }
}
