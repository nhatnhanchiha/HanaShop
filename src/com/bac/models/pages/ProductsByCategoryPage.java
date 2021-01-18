package com.bac.models.pages;

import com.bac.models.components.carousel.Carousel;
import com.bac.models.components.carousel.FoodCard;
import com.bac.models.entities.Category;
import com.bac.models.entities.Product;

import java.util.List;

public class ProductsByCategoryPage extends Page {

    public final static int SIZE_OF_CARDS = 20;
    private static final long serialVersionUID = 6240615356103696830L;
    private Category category;
    private List<FoodCard> foodCards;
    private Carousel hotCarousel;
    private boolean hasNextPage;
    private int page;

    public ProductsByCategoryPage(List<Product> products, Carousel hotCarousel, List<Category> categories, int page) {
        super(categories);
        this.foodCards = FoodCard.mapping(products);
        this.hotCarousel = hotCarousel;
        this.page = page;
        if (foodCards.size() > SIZE_OF_CARDS) {
            hasNextPage = true;
            foodCards.remove(SIZE_OF_CARDS);
        } else {
            hasNextPage = false;
        }
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<FoodCard> getFoodCards() {
        return foodCards;
    }

    public void setFoodCards(List<FoodCard> foodCards) {
        this.foodCards = foodCards;
    }

    public Carousel getHotCarousel() {
        return hotCarousel;
    }

    public void setHotCarousel(Carousel hotCarousel) {
        this.hotCarousel = hotCarousel;
    }


    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
