package com.bac.models.pages;

import com.bac.models.components.carousel.Carousel;
import com.bac.models.components.carousel.FoodCard;
import com.bac.models.entities.Category;
import com.bac.models.entities.Product;

import java.util.List;

public class ProductsByCategoryPage {
    public final static int SIZE_OF_CARDS = 20;
    private Category category;
    private List<FoodCard> foodCards;
    private Carousel hotCarousel;
    private List<Category> categories;
    private boolean hasNextPage;
    private int page;

    public ProductsByCategoryPage(List<Product> products, Carousel hotCarousel, List<Category> categories, int page) {
        this.foodCards = FoodCard.mapping(products);
        this.hotCarousel = hotCarousel;
        this.categories = categories;
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

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
