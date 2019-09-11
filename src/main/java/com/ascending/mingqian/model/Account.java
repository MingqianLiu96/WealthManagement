package com.ascending.mingqian.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "accounts")

public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(View.Account.class)
    private long id;

    @Column(name = "balance")
    @JsonView(View.Account.class)
    private double balance;

    @Column(name = "account_type")
    @JsonView(View.Account.class)
    private String accountType;

    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name= "customer_id")
    @JsonView(View.Account.class)
    private Customer customer;

    @OneToMany(mappedBy = "account",cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    private Set<Record> records;

    @Override
    public int hashCode(){
        return Objects.hash(id,balance,accountType);
    }

    @Override
    public boolean equals(Object o){
        if(this==o) return true;
        if(o == null || this.getClass()!=o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id && balance == account.balance && accountType.equals(account.accountType);
    }

    public Account() {

    }
    public Account(double balance, String accountType) {
        this.balance = balance;
        this.accountType = accountType;
    }

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

//    public long getCustomers_id() {
//        return customerId;
//    }
//
//    public void setCustomers_id(long customers_id) {
//        this.customerId = customers_id;
//    }


    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<Record> getRecords() {
        try{
            int size = records.size();
        }
        catch (Exception e){
            return null;
        }
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
                ", records=" + getRecords() +
                '}';
    }


//    @Override
//    public String toString() {
//        return "Account{" +
//                "id=" + id +
//                ", balance=" + balance +
//                ", accountType='" + accountType + '\'' +
//                ", customer=" + customer +
//                ", records=" + records +
//                '}';
//    }

    public static void main(String[] args){
        Account a1 = new Account(300,"credit");
        Account a2 = new Account(500,"checking");
        Map<Account,Integer> map = new HashMap<>();
        map.put(a1,35);
        map.put(a2,36);
        System.out.println(map.size());
    }
}
