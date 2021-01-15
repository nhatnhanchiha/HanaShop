package com.bac.models.entities.builder;

import com.bac.models.entities.Category;
import com.bac.models.entities.Product;

import java.time.LocalDate;

public final class ProductBuilder {
    private Integer productId;
    private String name;
    private String shortDescription;
    private String imageUrl;
    private Category category;
    private Boolean status;
    private String longDescription;
    private Double price;
    private LocalDate createDate;
    private Integer quantitySold;
    private Integer categoryId;
    private Integer quantity;

    private ProductBuilder() {
    }

    public static ProductBuilder aProduct() {
        return new ProductBuilder();
    }

    public ProductBuilder withProductId(Integer productId) {
        this.productId = productId;
        return this;
    }

    public ProductBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ProductBuilder withShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
        return this;
    }

    public ProductBuilder withImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public ProductBuilder withCategory(Category category) {
        this.category = category;
        return this;
    }

    public ProductBuilder withStatus(Boolean status) {
        this.status = status;
        return this;
    }

    public ProductBuilder withLongDescription(String longDescription) {
        this.longDescription = longDescription;
        return this;
    }

    public ProductBuilder withPrice(Double price) {
        this.price = price;
        return this;
    }

    public ProductBuilder withCreateDate(LocalDate createDate) {
        this.createDate = createDate;
        return this;
    }

    public ProductBuilder withQuantitySold(Integer quantitySold) {
        this.quantitySold = quantitySold;
        return this;
    }

    public ProductBuilder withCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public ProductBuilder withQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public Product build() {
        Product product = new Product();
        product.setProductId(productId);
        product.setName(name);
        product.setShortDescription(shortDescription);
        product.setImageUrl(imageUrl);
        product.setCategory(category);
        product.setStatus(status);
        product.setLongDescription(longDescription);
        product.setPrice(price);
        product.setCreateDate(createDate);
        product.setQuantitySold(quantitySold);
        product.setCategoryId(categoryId);
        product.setQuantity(quantity);
        return product;
    }
}
