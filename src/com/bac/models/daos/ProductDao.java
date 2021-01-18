package com.bac.models.daos;

import com.bac.models.entities.Product;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ProductDao extends Dao {
    List<Product> queryAll(int categoryId, int status, int limit, int offset) throws SQLException;

    List<Product> queryByName(String name);

    List<Product> queryByCategoryId(Integer categoryId, int limit, int offset) throws SQLException;

    List<Product> queryHotListProductByCategoryId(Integer categoryId) throws SQLException;

    List<Product> queryByName(String name, int limit, int offset) throws SQLException;

    List<Product> queryByNameOrCategoryIdOrRangeMoney(String name, Integer categoryId,
                                                      double minPrice, double maxPrice,
                                                      int limit, int offset) throws SQLException;

    Product queryById(Integer productId) throws SQLException;

    Product delete(Integer productId) throws SQLException;

    Product update(Product product) throws SQLException;

    Product insert(Product product) throws SQLException;

    Product updateCategory(Integer productId, Integer categoryId) throws SQLException;

    Product updateStatus(Integer productId, int status) throws SQLException;

    boolean updateQuantity(Map<Product, Integer> quantitiesMap) throws SQLException;

    List<Product> queryListFavoriteByUsername(String username) throws SQLException;

    List<Product> queryListOrderTogether(Integer productId) throws SQLException;

    List<Product> queryListFavoriteOfHanaShop() throws SQLException;

}
