package com.bac.models.entities;

import java.io.Serializable;

public class GoogleUser implements Serializable {
    private static final long serialVersionUID = -8230345246687729154L;

    private String gmail;
    private String username;
    private Account account;



    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
