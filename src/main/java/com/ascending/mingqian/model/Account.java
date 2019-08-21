package com.ascending.mingqian.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

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
    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name= "user_id")
    private User user;

    @OneToMany(mappedBy = "account",cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    private Set<Record> records;


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

    public Set<Record> getRecords() {
        return records;
    }

    public void setRecords(Set<Record> records) {
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


//    @Override
//    public String toString() {
//        return "Account{" +
//                "id=" + id +
//                ", balance=" + balance +
//                ", accountType='" + accountType + '\'' +
//                ", user=" + user +
//                ", records=" + records +
//                '}';
//    }
}
