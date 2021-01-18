package com.bac.models.pages;

import com.bac.models.entities.Category;

import java.util.List;

public class RegisterPage extends Page {

    private static final long serialVersionUID = -8507162394286654971L;
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
