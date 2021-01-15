package com.bac.models.pages;

import com.bac.models.entities.Category;
import com.bac.models.entities.Product;

import java.util.List;

/**
 * @author nhatn
 */
public class ListProductManagementPage extends Page {
    private List<Product> products;
    private int currentPage;

    public ListProductManagementPage(List<Category> categories, List<Product> products, int currentPage) {
        super(categories);
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
