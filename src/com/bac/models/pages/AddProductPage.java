package com.bac.models.pages;

import com.bac.models.entities.Category;

import java.util.List;

public class AddProductPage extends Page {
    private static final long serialVersionUID = 1426045523848700974L;

    public AddProductPage(List<Category> categories) {
        super(categories);
    }
}
