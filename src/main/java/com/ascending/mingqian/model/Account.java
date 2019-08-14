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

//    @Column(name = "user_id")
//    private long userId;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name= "user_id")
    private User user;

    @OneToMany(mappedBy = "account",cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    private List<Record> records;


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

//    public long getUsers_id() {
//        return userId;
//    }
//
//    public void setUsers_id(long users_id) {
//        this.userId = users_id;
//    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", balance=" + balance +
                ", accountType='" + accountType + '\'' +
                ", records=" + records +
                '}';
    }
}
