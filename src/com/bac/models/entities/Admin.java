package com.bac.models.entities;

import java.io.Serializable;

/**
 * @author nhatn
 */
public class Admin implements Serializable {
    private static final long serialVersionUID = 761707857589394172L;
    private String username;
    private String password;
    private String name;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
