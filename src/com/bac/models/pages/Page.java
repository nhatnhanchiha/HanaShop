package com.bac.models.pages;

import com.bac.models.components.carousel.Carousel;
import com.bac.models.entities.Category;

import java.util.List;

public class Page {
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
