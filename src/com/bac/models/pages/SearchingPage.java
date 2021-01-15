package com.bac.models.pages;

import com.bac.models.entities.Category;
import com.bac.models.entities.Product;

import java.util.List;

public class SearchingPage {
    private List<Product> products;
    private List<Category> categories;
    private int page;
    private boolean hasNextPage;
    public static final int SIZE_OF_PRODUCTS = 20;

    public SearchingPage(List<Product> products, List<Category> categories, int page) {
        this.products = products;
        this.categories = categories;
        this.page = page;
        System.out.println("products.size() = " + products.size());
        if (products.size() == SIZE_OF_PRODUCTS + 1) {
            hasNextPage = true;
            products.remove(SIZE_OF_PRODUCTS);
        } else {
            hasNextPage = false;
        }
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
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

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }
}
