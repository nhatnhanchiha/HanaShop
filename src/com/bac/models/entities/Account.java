package com.bac.models.entities;

import java.io.Serializable;

/**
 * @author nhatn
 */
public class Account implements Serializable {
    private static final long serialVersionUID = 8919709163333132958L;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Boolean status;

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
