package com.ascending.mingqian.model;

public class AccountInfo {
    private int id;
    private double balance;
    private String accountType;
    private int users_id;


    public  int getId(){
        return id;
    }

    public String getAccountType(){
        return accountType;
    }

    public double getBalance() {
        return balance;
    }

    public  int getUsers_id(){
        return users_id;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public void setUsers_id(int users_id) {
        this.users_id = users_id;
    }

}
