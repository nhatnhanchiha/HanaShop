package com.bac.models.components.carousel;

import java.util.List;

/**
 * @author nhatn
 */
public class Carousel {
    private int size;
    private String name;
    private Integer categoryId;
    private List<Slide> slides;

    public Carousel(String name, Integer categoryId, List<FoodCard> cards) {
        this.name = name;
        this.categoryId = categoryId;
        slides = Slide.exchangeCardsToSlides(cards);
        this.size = cards.size() % 4 == 0 ? cards.size() / 4 : cards.size() / 4 + 1;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public List<Slide> getSlides() {
        return slides;
    }

    public void setSlides(List<Slide> slides) {
        this.slides = slides;
    }
}