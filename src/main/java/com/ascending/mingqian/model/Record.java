package com.ascending.mingqian.model;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "record")

public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "type")
    private  String type;

    @Column(name = "amount")
    private double amount;

    @Column(name = "date")
    private Date date;

    @Column(name = "description")
    private String description;

    @Column(name = "account_id")
    private int account_id;

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

    public int getAccount_id(){
        return account_id;
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

    public void setAccount_id(int account_id){
        this.account_id = account_id;
    }

}
