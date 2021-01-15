package com.bac.models.components;

import com.bac.models.entities.Product;

import java.util.List;

/**
 * @author nhatn
 */
public class HotTodayList {
    private List<Product> products;

    public HotTodayList(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
