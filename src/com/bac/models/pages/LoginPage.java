package com.bac.models.pages;

import com.bac.models.entities.Category;

import java.util.List;

public class LoginPage {
    private List<Category> categories;
    private String message;

    public LoginPage(List<Category> categories, String message) {
        this.categories = categories;
        this.message = message;
    }

    public LoginPage(List<Category> categories) {
        this.categories = categories;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
