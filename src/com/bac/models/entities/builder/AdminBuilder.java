package com.bac.models.entities.builder;

import com.bac.models.entities.Admin;

public final class AdminBuilder {
    private String username;
    private String password;
    private String name;

    private AdminBuilder() {
    }

    public static AdminBuilder anAdmin() {
        return new AdminBuilder();
    }

    public AdminBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public AdminBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public AdminBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public Admin build() {
        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(password);
        admin.setName(name);
        return admin;
    }
}
