package ru.rsoi.authserver.model;

public class UserModel {

    private String last_name;

    private String first_name;

    private String second_name;

    private String address;

    private String bank;

    private String inn;

    private long uid;
    private String userlogin;
    private String userpassword;

    public UserModel() {
    }

    public UserModel(String last_name, String first_name, String second_name, String address, String bank, String inn, String login, String password) {
        this.last_name = last_name;
        this.first_name = first_name;
        this.second_name = second_name;
        this.address = address;
        this.bank = bank;
        this.inn = inn;
        this.userlogin = login;
        this.userpassword=password;
    }

    public String getLogin() {
        return userlogin;
    }

    public void setLogin(String login) {
        this.userlogin = login;
    }

    public String getPassword() {
        return userpassword;
    }

    public void setPassword(String password) {
        this.userpassword = password;
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

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }
}