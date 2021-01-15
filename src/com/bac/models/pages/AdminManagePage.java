package com.bac.models.pages;

import com.bac.models.entities.Category;
import com.bac.models.entities.Product;

import java.util.List;

public class AdminManagePage {
    public static final int SIZE_OF_PRODUCTS = 20;
    private List<Product> products;
    private List<Category> categories;
    private int page;
    private boolean hasNextPage;
    private String message;

    public AdminManagePage(List<Product> products, List<Category> categories, int page) {
        this.products = products;
        this.categories = categories;
        this.page = page;
        if (products.size() > SIZE_OF_PRODUCTS) {
            hasNextPage = true;
            products.remove(SIZE_OF_PRODUCTS);
        } else {
            hasNextPage = false;
        }
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
