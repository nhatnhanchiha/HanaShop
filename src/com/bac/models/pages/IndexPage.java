package com.bac.models.pages;

import com.bac.models.components.carousel.Carousel;
import com.bac.models.entities.Category;

import java.util.List;

/**
 * @author nhatn
 */
public class IndexPage extends Page {

    private static final long serialVersionUID = -8420588584591241399L;
    private List<Carousel> carousels;

    public IndexPage(List<Carousel> carousels, List<Category> categories) {
        super(categories);
        this.carousels = carousels;
    }

    public List<Carousel> getCarousels() {
        return carousels;
    }

    public void setCarousels(List<Carousel> carousels) {
        this.carousels = carousels;
    }

}
