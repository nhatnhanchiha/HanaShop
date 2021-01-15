package com.bac.models.entities;

import java.util.List;

/**
 * @author nhatn
 */
public class Category {
    private Integer categoryId;
    private String categoryName;
    private List<Product> products;
    private String description;
    private Boolean inIndex;
    private int sizeOnIndex;
    private Boolean status;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getInIndex() {
        return inIndex;
    }

    public void setInIndex(Boolean inIndex) {
        this.inIndex = inIndex;
    }

    public int getSizeOnIndex() {
        return sizeOnIndex;
    }

    public void setSizeOnIndex(int sizeOnIndex) {
        this.sizeOnIndex = sizeOnIndex;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
