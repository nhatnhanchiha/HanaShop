package com.bac.models.pages;

import com.bac.models.entities.Category;
import com.bac.models.entities.Product;

import java.util.List;

public class EditProductPage extends Page {
    private Product product;


    public EditProductPage(List<Category> categories, Product product) {
        super(categories);
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
