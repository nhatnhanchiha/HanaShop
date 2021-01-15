package com.bac.models.services.impl;

import com.bac.models.components.carousel.Carousel;
import com.bac.models.components.carousel.FoodCard;
import com.bac.models.daos.AdminDao;
import com.bac.models.daos.CategoryDao;
import com.bac.models.daos.LogSqlDao;
import com.bac.models.daos.ProductDao;
import com.bac.models.daos.impls.LogSqlDaoImpl;
import com.bac.models.entities.Admin;
import com.bac.models.entities.Category;
import com.bac.models.entities.LogSql;
import com.bac.models.entities.Product;
import com.bac.models.entities.builder.LogSqlBuilder;
import com.bac.models.services.ProductService;
import com.bac.models.utilities.HanaShopContext;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author nhatn
 */
public class ProductServiceImpl implements ProductService {
    private final HanaShopContext hanaShopContext;


    public ProductServiceImpl(HanaShopContext hanaShopContext) {
        this.hanaShopContext = hanaShopContext;
    }

    @Override
    public List<Product> getAllProductByLimitAndOffset(int categoryId, int status, int limit, int offset) throws SQLException {
        ProductDao productDao = hanaShopContext.getProductDao();
        return productDao.queryAll(categoryId, status, limit, offset);
    }

    @Override
    public List<Carousel> getAllCarouselOnIndex() throws SQLException {
        List<Carousel> carousels = new ArrayList<>();
        HanaShopContext hanaShopContext = this.hanaShopContext;
        CategoryDao categoryDao = hanaShopContext.getCategoryDao();
        ProductDao productDao = hanaShopContext.getProductDao();
        List<Category> categories = categoryDao.queryAllCategoryOnIndex();
        for (Category category : categories) {
            List<Product> products = productDao.queryByCategoryId(category.getCategoryId(), category.getSizeOnIndex() * 4, 0);
            List<FoodCard> cards = FoodCard.mapping(products);
            Carousel carousel = new Carousel(category.getCategoryName(),
                    category.getCategoryId(), cards);
            carousels.add(carousel);
        }
        return carousels;
    }

    @Override
    public List<Category> getAllCategory() throws SQLException {
        CategoryDao categoryDao = hanaShopContext.getCategoryDao();
        return categoryDao.queryAll();
    }

    @Override
    public Product getProductById(Integer productId) throws SQLException {
        ProductDao productDao = hanaShopContext.getProductDao();
        return productDao.queryById(productId);
    }

    @Override
    public Product deleteProduct(Integer productId, String username) throws SQLException {
        AdminDao adminDao = hanaShopContext.getAdminDao();
        Admin admin = adminDao.queryByUsername(username);
        Product productInDb = null;
        if (admin != null) {
            ProductDao productDao = hanaShopContext.getProductDao();
            Product product = productDao.queryById(productId);
            if (product != null && product.getStatus()) {
                productInDb = productDao.delete(productId);
                if (productInDb != null) {
                    LogSql logSql = LogSqlBuilder.aLogSql()
                            .withType(LogSql.Type.DELETE)
                            .withProductId(productInDb.getProductId())
                            .withUpdatedDate(LocalDateTime.now())
                            .withUpdatedUser(admin.getUsername())
                            .build();
                    LogSqlDao logSqlDao = hanaShopContext.getLogSqlDao();
                    LogSql logSqlInDb = logSqlDao.insert(logSql);
                    if (logSqlInDb != null) {
                        hanaShopContext.saveChanges();
                    } else {
                        hanaShopContext.rollback();
                        return null;
                    }
                } else {
                    hanaShopContext.rollback();
                    return null;
                }
            }
        } else {
            hanaShopContext.rollback();
        }

        return productInDb;
    }

    @Override
    public Product addProduct(Product product, String username) throws SQLException {
        AdminDao adminDao = hanaShopContext.getAdminDao();
        Admin admin = adminDao.queryByUsername(username);
        Product productInDb;
        if (admin != null) {
            ProductDao productDao = hanaShopContext.getProductDao();
            productInDb = productDao.insert(product);
            if (productInDb != null) {
                LogSql logSql = LogSqlBuilder.aLogSql()
                        .withType(LogSql.Type.CREATE)
                        .withProductId(productInDb.getProductId())
                        .withUpdatedDate(LocalDateTime.now())
                        .withUpdatedUser(admin.getUsername())
                        .build();
                LogSqlDao logSqlDao = hanaShopContext.getLogSqlDao();
                LogSql logSqlInDb = logSqlDao.insert(logSql);
                if (logSqlInDb != null) {
                    hanaShopContext.saveChanges();
                    return productInDb;
                } else {
                    hanaShopContext.rollback();
                    return null;
                }
            } else {
                hanaShopContext.rollback();
                return null;
            }
        } else {
            hanaShopContext.rollback();
            return null;
        }
    }

    @Override
    public Product updateProduct(Product product, String username) throws SQLException {
        AdminDao adminDao = hanaShopContext.getAdminDao();
        Admin admin = adminDao.queryByUsername(username);
        Product productInDb;
        if (admin != null) {
            CategoryDao categoryDao = hanaShopContext.getCategoryDao();
            Category category = categoryDao.queryById(product.getCategoryId());
            if (category == null) {
                hanaShopContext.rollback();
                return null;
            }
            ProductDao productDao = hanaShopContext.getProductDao();
            productInDb = productDao.update(product);
            if (productInDb != null) {
                LogSql logSql = LogSqlBuilder.aLogSql()
                        .withType(LogSql.Type.UPDATE)
                        .withProductId(productInDb.getProductId())
                        .withUpdatedDate(LocalDateTime.now())
                        .withUpdatedUser(admin.getUsername())
                        .build();
                LogSqlDao logSqlDao = hanaShopContext.getLogSqlDao();
                LogSql log = logSqlDao.insert(logSql);
                if (log != null) {
                    hanaShopContext.saveChanges();
                    return productInDb;
                } else {
                    hanaShopContext.rollback();
                    return null;
                }
            } else {
                hanaShopContext.rollback();
                return null;
            }
        } else {
            hanaShopContext.rollback();
            return null;
        }
    }

    @Override
    public List<Product> getProductDetails(Set<Product> products) throws SQLException {
        List<Product> result = new ArrayList<>();
        for (Product product : products) {
            ProductDao productDao = hanaShopContext.getProductDao();
            Product productInDb = productDao.queryById(product.getProductId());
            if (productInDb != null) {
                result.add(productInDb);
            }
        }
        return result;
    }

    @Override
    public List<Product> getProductByTypeOrderByCreateDate(Integer categoryId, int limit, int offset) throws SQLException {
        ProductDao productDao = hanaShopContext.getProductDao();
        return productDao.queryByCategoryId(categoryId, limit, offset);
    }

    @Override
    public List<Product> searchProductByName(String name, int limit, int offset) throws SQLException {
        ProductDao productDao = hanaShopContext.getProductDao();
        return productDao.queryByName(name, limit, offset);
    }

    @Override
    public List<Product> searchProductByNameOrCategoryOrMoneyRange(String name, Integer categoryId, double minPrice, double maxPrice, int limit, int offset) throws SQLException {
        ProductDao productDao = hanaShopContext.getProductDao();
        return productDao.queryByNameOrCategoryIdOrRangeMoney(name, categoryId, minPrice, maxPrice, limit, offset);
    }

    @Override
    public Carousel getHotCarouselOfCategory(Integer categoryId) throws SQLException {
        ProductDao productDao = hanaShopContext.getProductDao();
        List<Product> products = productDao.queryHotListProductByCategoryId(categoryId);
        List<FoodCard> foodCards = FoodCard.mapping(products);
        return new Carousel("Hot nhất hiện nay", categoryId, foodCards);
    }

    @Override
    public Product updateCategory(Integer productId, Integer categoryId, String admin) throws SQLException {
        AdminDao adminDao = hanaShopContext.getAdminDao();
        Admin adminInDb = adminDao.queryByUsername(admin);
        if (adminInDb != null) {
            CategoryDao categoryDao = hanaShopContext.getCategoryDao();
            Category category = categoryDao.queryById(categoryId);
            if (category != null) {
                ProductDao productDao = hanaShopContext.getProductDao();
                Product product = productDao.queryById(productId);
                if (product != null) {
                    Product productInDb = productDao.updateCategory(productId, categoryId);
                    if (productInDb != null) {
                        LogSql logSql = LogSqlBuilder.aLogSql()
                                .withType(LogSql.Type.UPDATE)
                                .withProductId(productId)
                                .withUpdatedDate(LocalDateTime.now())
                                .withUpdatedUser(adminInDb.getUsername())
                                .build();
                        LogSqlDao logSqlDao = hanaShopContext.getLogSqlDao();
                        LogSql log = logSqlDao.insert(logSql);
                        if (log != null) {
                            return productInDb;
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Product updateStatus(Integer productId, int status, String admin) throws SQLException {
        AdminDao adminDao = hanaShopContext.getAdminDao();
        Admin adminInDb = adminDao.queryByUsername(admin);
        if (adminInDb != null) {
            ProductDao productDao = hanaShopContext.getProductDao();
            Product product = productDao.queryById(productId);
            if (product != null) {
                Product productInDb = productDao.updateStatus(productId, status);
                if (productInDb != null) {
                    LogSql logSql = LogSqlBuilder.aLogSql()
                            .withType(LogSql.Type.UPDATE)
                            .withProductId(productId)
                            .withUpdatedDate(LocalDateTime.now())
                            .withUpdatedUser(adminInDb.getUsername())
                            .build();
                    LogSqlDao logSqlDao = hanaShopContext.getLogSqlDao();
                    LogSql log = logSqlDao.insert(logSql);
                    if (log != null) {
                        return productInDb;
                    }
                }
            }
        }
        return null;
    }
}