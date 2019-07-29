package com.ascending.mingqian.model;

import java.util.Date;

public class Record {
    private int id;
    private  String type;
    private double amount;
    private Date date;
    private String description;
    private int accountInfo_id;

    public int getId(){
        return id;
    }

    public String getType(){
        return type;
    }

    public double getAmount(){
        return amount;
    }

    public Date getDate(){
        return date;
    }

    public String getDescription(){
        return description;
    }

    public int getAccountInfo_id(){
        return accountInfo_id;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setType(String type){
        this.type = type;
    }

    public void setAmount(double amount){
        this.amount = amount;
    }

    public void  setDate(Date date){
        this.date = date;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setAccountInfo_id(int accountInfo_id){
        this.accountInfo_id = accountInfo_id;
    }

}
