package com.ascending.mingqian.model;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "customers")
public class Customer {
    public Customer(){}
    public Customer(Long id, String name, String password){
        this.id = id;
        this.name = name;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(View.Customer.class)
    private long id;

    @Column(name = "name")
    @JsonView(View.Customer.class)
    private String name;

    @Column(name = "password")
    @JsonView(View.Customer.class)
    private String password;

    @OneToMany(mappedBy = "customer",cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    private Set<Account> accounts;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id &&
                name.equals(customer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public  long getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Account> getAccounts() {
        try{
            int size = accounts.size();
        }
        catch (Exception e){
            return null;
        }

            return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", accounts=" + getAccounts() +
                '}';
    }
}
