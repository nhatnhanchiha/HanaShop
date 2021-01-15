package com.bac.models.entities.builder;

import com.bac.models.entities.Account;

public final class AccountBuilder {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Boolean status;

    private AccountBuilder() {
    }

    public static AccountBuilder anAccount() {
        return new AccountBuilder();
    }

    public AccountBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public AccountBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public AccountBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public AccountBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public AccountBuilder withStatus(Boolean status) {
        this.status = status;
        return this;
    }

    public Account build() {
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setStatus(status);
        return account;
    }
}
