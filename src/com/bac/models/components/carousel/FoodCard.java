package com.bac.models.components.carousel;

import com.bac.models.entities.Product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nhatn
 */
public class FoodCard implements Serializable {

    private static final long serialVersionUID = 802419926452196025L;
    private Integer productId;
    private String name;
    private String shortDescription;
    private Double price;
    private String imageUrl;

    public FoodCard(Product product) {
        this.productId = product.getProductId();
        this.name = product.getName();
        this.shortDescription = product.getShortDescription();
        this.imageUrl = product.getImageUrl();
        this.price = product.getPrice();
    }

    public static List<FoodCard> mapping(List<Product> products) {
        List<FoodCard> cards = new ArrayList<>();
        for (Product product : products) {
            FoodCard foodCard = new FoodCard(product);
            cards.add(foodCard);
        }
        return cards;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}