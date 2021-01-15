package com.bac.models.pages;

import com.bac.models.components.carousel.Carousel;
import com.bac.models.entities.Category;

import java.util.List;

/**
 * @author nhatn
 */
public class IndexPage {
    private List<Carousel> carousels;
    private List<Category> categories;

    public IndexPage(List<Carousel> carousels, List<Category> categories) {
        this.carousels = carousels;
        this.categories = categories;
    }

    public List<Carousel> getCarousels() {
        return carousels;
    }

    public void setCarousels(List<Carousel> carousels) {
        this.carousels = carousels;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
