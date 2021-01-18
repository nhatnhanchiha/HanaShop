package com.bac.models.components.cart;

import com.bac.models.entities.Product;
import com.bac.models.entities.builder.ProductBuilder;

import java.util.HashMap;
import java.util.List;

/**
 * @author nhatn
 */
public class CartObject extends HashMap<Product, Integer> {
    private static final long serialVersionUID = -1543646210516752252L;
    private boolean hasDetail;

    public void addProduct(Integer productId) {
        Product product = ProductBuilder.aProduct()
                .withProductId(productId)
                .build();
        this.putIfAbsent(product, 0);
        this.put(product, this.get(product) + 1);
    }

    public void removeProduct(Integer productId) {
        Product product = ProductBuilder.aProduct()
                .withProductId(productId)
                .build();
        this.remove(product);
    }

    public void loadProductDetails(List<Product> products) {
        for (Product product : products) {
            this.putIfAbsent(product, 0);
            Integer quantity = this.get(product);
            if (quantity > product.getQuantity()) {
                product.setStatus(false);
            }
            this.remove(product);
            this.put(product, quantity);
            hasDetail = true;
        }
    }

    public void changeQuantity(Integer productId, int quantity) {
        Product product = ProductBuilder.aProduct()
                .withProductId(productId)
                .build();
        if (this.containsKey(product)) {
            this.put(product, quantity);
        }
    }

    public boolean isHasDetail() {
        return hasDetail;
    }
}
