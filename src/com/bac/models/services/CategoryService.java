package com.bac.models.services;

import com.bac.models.entities.Category;

import java.sql.SQLException;

public interface CategoryService {
    Category getCategoryById(int categoryId) throws SQLException;
}
