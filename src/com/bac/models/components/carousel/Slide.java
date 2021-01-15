package com.bac.models.components.carousel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nhatn
 */
public class Slide {
    private final List<FoodCard> cards;
    private static final int SIZE = 4;

    public Slide() {
        cards = new ArrayList<>(SIZE);
    }

    public void addFoodCard(FoodCard card) {
        cards.add(card);
    }

    public static List<Slide> exchangeCardsToSlides(List<FoodCard> cards) {
        List<Slide> slides = new ArrayList<>();
        Slide temp = new Slide();
        for (FoodCard card : cards) {
            if (temp.cards.size() == SIZE) {
                slides.add(temp);
                temp = new Slide();
            }
            temp.addFoodCard(card);
        }

        if (temp.cards.size() > 0) {
            slides.add(temp);
        }
        return slides;
    }

    public List<FoodCard> getCards() {
        return cards;
    }
}
