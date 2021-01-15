package com.bac.models.entities.builder;

import com.bac.models.entities.Category;
import com.bac.models.entities.Product;

import java.util.List;

public final class CategoryBuilder {
    private Integer categoryId;
    private String categoryName;
    private List<Product> products;
    private String description;
    private Boolean inIndex;
    private int sizeOnIndex;
    private Boolean status;

    private CategoryBuilder() {
    }

    public static CategoryBuilder aCategory() {
        return new CategoryBuilder();
    }

    public CategoryBuilder withCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public CategoryBuilder withCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    public CategoryBuilder withProducts(List<Product> products) {
        this.products = products;
        return this;
    }

    public CategoryBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public CategoryBuilder withInIndex(Boolean inIndex) {
        this.inIndex = inIndex;
        return this;
    }

    public CategoryBuilder withSizeOnIndex(int sizeOnIndex) {
        this.sizeOnIndex = sizeOnIndex;
        return this;
    }

    public CategoryBuilder withStatus(Boolean status) {
        this.status = status;
        return this;
    }

    public Category build() {
        Category category = new Category();
        category.setCategoryId(categoryId);
        category.setCategoryName(categoryName);
        category.setProducts(products);
        category.setDescription(description);
        category.setInIndex(inIndex);
        category.setSizeOnIndex(sizeOnIndex);
        category.setStatus(status);
        return category;
    }
}
