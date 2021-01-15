package com.bac.models.pages;

import com.bac.models.entities.Category;

import java.util.List;

public class RegisterPage extends Page {
    private String registerAccountDoubleErr;

    public RegisterPage(List<Category> categories, String registerAccountDoubleErr) {
        super(categories);
        this.registerAccountDoubleErr = registerAccountDoubleErr;
    }

    public String getRegisterAccountDoubleErr() {
        return registerAccountDoubleErr;
    }

    public void setRegisterAccountDoubleErr(String registerAccountDoubleErr) {
        this.registerAccountDoubleErr = registerAccountDoubleErr;
    }
}
