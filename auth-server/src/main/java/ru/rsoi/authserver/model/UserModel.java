package ru.rsoi.authserver.model;

public class UserModel {

    private String last_name;

    private String first_name;

    private String second_name;

    private long uid;
    private String userlogin;
    private String userpassword;
    private String authorities;

    private boolean enabled;

    public UserModel() {
    }

    public UserModel(String last_name, String first_name, String second_name, String login, String password, String authorities, boolean enabled) {
        this.last_name = last_name;
        this.first_name = first_name;
        this.second_name = second_name;
        this.userlogin = login;
        this.userpassword=password;
        this.authorities=authorities;
        this.enabled = enabled;

    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
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

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }
}
