package com.bac.models.pages;

import com.bac.models.entities.Category;

import java.util.List;

public class LoginPage extends Page {

    private static final long serialVersionUID = 4319306641400256540L;
    private String message;

    public LoginPage(List<Category> categories, String message) {
        super(categories);
        this.message = message;
    }

    public LoginPage(List<Category> categories) {
        super(categories);
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
