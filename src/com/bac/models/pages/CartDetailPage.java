package com.bac.models.pages;

import com.bac.models.components.Cart;
import com.bac.models.entities.Category;

import java.util.List;

/**
 * @author nhatn
 */
public class CartDetailPage extends Page {
    public final static int CASH_PAYMENT_UPON_DELIVERY = 0;
    public final static int CASH_PAYMENT_PAYPAL = 1;

    private static final long serialVersionUID = -617143249058444081L;
    private Cart cart;
    public CartDetailPage(List<Category> categories) {
        super(categories);
    }

    public CartDetailPage(List<Category> categories, Cart cart) {
        super(categories);
        this.cart = cart;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
