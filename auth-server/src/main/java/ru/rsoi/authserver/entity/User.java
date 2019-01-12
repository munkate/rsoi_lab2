package ru.rsoi.authserver.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

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

    @Column(name = "username", length = 20)
    private String userlogin;
    @Column(name = "password", length = 20)
    private String password;

    @Column(name = "uid", unique = true)
    private long uid;

    @Column(name="authorities")
    private String authorities;

    @Column(name="token")
    private String token;

    @Column(name="token_validity")
    private int validity;

    @Column(name="last_activity_time")
    private Timestamp timestamp;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getValidity() {
        return validity;
    }

    public void setValidity(int validity) {
        this.validity = validity;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public User() {
    }

    public User(String last_name, String first_name, String second_name, String login, String password, String authorities) {
        this.last_name = last_name;
        this.first_name = first_name;
        this.second_name = second_name;
        this.userlogin = login;
        this.password = password;
        this.authorities = authorities;
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

}
