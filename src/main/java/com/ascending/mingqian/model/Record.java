package com.ascending.mingqian.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "records")

public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "type")
    private  String type;

    @Column(name = "amount")
    private double amount;

    @Column(name = "date")
    private Date date;

    @Column(name = "description")
    private String description;

//    @Column(name = "account_id")
//    private long accountId;

    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name= "account_id")
    private Account account;

    public long getId(){
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

//    public long getAccount_id(){
//        return accountId;
//    }

    public void setId(long id){
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

//    public void setAccount_id(long account_id){
//
//        this.accountId = account_id;
//    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", date=" + date +
                ", description='" + description + '\'' +
              // ", account=" + account +
                '}';
    }
}
