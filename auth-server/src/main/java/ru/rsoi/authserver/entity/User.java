package ru.rsoi.authserver.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users",schema = "public")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "last_name", length = 20)
    private String last_name;

    @Column(name = "first_name", length = 20)
    private String first_name;

    @Column(name = "second_name", length = 20)
    private String second_name;

    @Column(name = "address", length = 50)
    private String address;

    @Column(name = "bank", length = 50)
    private String bank;

    @Column(name = "inn", length = 20)
    private String inn;

    @Column(name = "login", length = 20)
    private String userlogin;
    @Column(name = "password", length = 20)
    private String password;

    @Column(name = "uid", unique = true)
    private long uid;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public User() {
    }

    public User(String last_name, String first_name, String second_name, String address, String bank, String inn, String login, String password) {
        this.last_name = last_name;
        this.first_name = first_name;
        this.second_name = second_name;
        this.address = address;
        this.bank = bank;
        this.inn = inn;
        this.userlogin = login;
        this.password = password;
    }

    public String getLogin() {
        return userlogin;
    }

    public void setLogin(String login) {
        this.userlogin = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getSecond_name() {
        return second_name;
    }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

}
