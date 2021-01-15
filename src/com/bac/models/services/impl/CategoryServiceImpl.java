package com.bac.models.services.impl;

import com.bac.models.daos.CategoryDao;
import com.bac.models.entities.Category;
import com.bac.models.services.CategoryService;
import com.bac.models.utilities.HanaShopContext;

import java.sql.SQLException;

/**
 * @author nhatn
 */
public class CategoryServiceImpl implements CategoryService {
    private final HanaShopContext hanaShopContext;

    public CategoryServiceImpl(HanaShopContext hanaShopContext) {
        this.hanaShopContext = hanaShopContext;
    }

    @Override
    public Category getCategoryById(int categoryId) throws SQLException {
        CategoryDao categoryDao = hanaShopContext.getCategoryDao();
        return categoryDao.queryById(categoryId);
    }
}