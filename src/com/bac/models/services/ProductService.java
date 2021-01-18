package com.bac.models.services;

import com.bac.models.components.carousel.Carousel;
import com.bac.models.components.cart.CartObject;
import com.bac.models.entities.Category;
import com.bac.models.entities.Product;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface ProductService {
    List<Product> getAllProductByLimitAndOffset(int categoryId, int status, int limit, int offset) throws SQLException;

    List<Carousel> getAllCarouselOnIndex() throws SQLException;

    List<Category> getAllCategory() throws SQLException;

    Product getProductById(Integer productId) throws SQLException;

    Product deleteProduct(Integer productId, String username) throws SQLException;

    Product addProduct(Product product, String username) throws SQLException;

    Product updateProduct(Product product, String username) throws SQLException;

    List<Product> getProductDetails(Set<Product> products) throws SQLException;

    List<Product> getProductByTypeOrderByCreateDate(Integer categoryId, int limit, int offset) throws SQLException;

    List<Product> searchProductByName(String name, int limit, int offset) throws SQLException;

    List<Product> searchProductByNameOrCategoryOrMoneyRange(String name, Integer categoryId,
                                                            double minPrice, double maxPrice,
                                                            int limit, int offset) throws SQLException;

    Carousel getHotCarouselOfCategory(Integer categoryId) throws SQLException;

    Product updateCategory(Integer productId, Integer categoryId, String admin) throws SQLException;

    Product updateStatus(Integer productId, int status, String admin) throws SQLException;

    boolean validCartObject(CartObject cartObject) throws SQLException;

    boolean updateQuantities(CartObject cartObject) throws SQLException;

    List<Product> getListFavoriteOfUsername(String username) throws SQLException;

    List<Product> getListFavoriteOfHanaShop() throws SQLException;

    List<Product> getListUserOrderTogetherProductId(Integer productId) throws SQLException;
}