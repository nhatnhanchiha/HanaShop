package com.bac.models.components;

import com.bac.models.components.cart.CartObject;
import com.bac.models.entities.Product;

import java.io.Serializable;

public class Cart implements Serializable {

    private static final long serialVersionUID = -3648790062171618145L;
    private CartObject cartObject;
    private Double sum;

    public Cart(CartObject cartObject) {
        this.cartObject = cartObject;
        this.sum = 0.0;
        for (Product product : cartObject.keySet()) {
            if (product.getStatus()) {
                sum += product.getPrice() * cartObject.get(product);
            }
        }
    }

    public CartObject getCartObject() {
        return cartObject;
    }

    public void setCartObject(CartObject cartObject) {
        this.cartObject = cartObject;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }
}
