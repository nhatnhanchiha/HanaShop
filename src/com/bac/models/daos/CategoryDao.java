package com.bac.models.daos;

import com.bac.models.entities.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryDao extends Dao {
    List<Category> queryAll() throws SQLException;
    List<Category> queryAllCategoryOnIndex() throws SQLException;
    Category queryById(Integer categoryId) throws SQLException;
}
