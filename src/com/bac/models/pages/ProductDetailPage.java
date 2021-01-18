package com.bac.models.pages;

import com.bac.models.components.carousel.Carousel;
import com.bac.models.entities.Category;
import com.bac.models.entities.Product;

import java.util.List;

public class ProductDetailPage extends Page {
    private Product product;
    private Carousel carousel;

    public ProductDetailPage(List<Category> categories, Product product, Carousel carousel) {
        super(categories);
        this.product = product;
        this.carousel = carousel;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Carousel getCarousel() {
        return carousel;
    }

    public void setCarousel(Carousel carousel) {
        this.carousel = carousel;
    }
}
