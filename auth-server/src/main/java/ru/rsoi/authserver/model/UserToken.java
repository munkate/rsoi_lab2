package ru.rsoi.authserver.model;

import java.util.UUID;

public class UserToken {
    private int validity=1800;
    private String value;


    public UserToken()
    {
        this.value= UUID.randomUUID().toString();
    }

    public int getValidity() {
        return validity;
    }
    public String getValue(){
        return value;
    }

}
