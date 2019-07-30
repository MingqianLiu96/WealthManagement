package com.ascending.mingqian.model;

import java.util.Objects;

public class Users {
    private int id;
    private String name;
    private String password;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return id == users.id &&
                name.equals(users.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public  int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
