package com.bac.models.pages;

import com.bac.models.entities.Category;
import com.bac.models.entities.Product;

import java.util.List;

public class SearchingPage extends Page {
    public static final int SIZE_OF_PRODUCTS = 20;
    private static final long serialVersionUID = -3617512072858341205L;

    private List<Product> products;
    private int page;
    private boolean hasNextPage;
    private List<Product> listSuggestion;

    public SearchingPage(List<Product> products, List<Category> categories, int page, List<Product> listSuggestion) {
        super(categories);

        this.products = products;
        this.page = page;
        System.out.println("products.size() = " + products.size());
        if (products.size() == SIZE_OF_PRODUCTS + 1) {
            hasNextPage = true;
            products.remove(SIZE_OF_PRODUCTS);
        } else {
            hasNextPage = false;
        }
        this.listSuggestion = listSuggestion;
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

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public List<Product> getListSuggestion() {
        return listSuggestion;
    }

    public void setListSuggestion(List<Product> listSuggestion) {
        this.listSuggestion = listSuggestion;
    }
}
