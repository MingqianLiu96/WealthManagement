package com.ascending.mingqian.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {
    public User(){}
    public User(Long id, String name, String password){
        this.id = id;
        this.name = name;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    private List<Account> accounts;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                name.equals(user.name);
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

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", accounts=" + accounts +
                '}';
    }
}
