package com.bac.models.pages;

import com.bac.models.entities.Category;

import java.io.Serializable;
import java.util.List;

public class Page implements Serializable {
    protected List<Category> categories;

    public Page(List<Category> categories) {
        this.categories = categories;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
