package com.bac.models.daos.impls;

import com.bac.models.daos.CategoryDao;
import com.bac.models.entities.Category;
import com.bac.models.entities.builder.CategoryBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nhatn
 */
public class CategoryDaoImpl implements CategoryDao {
    private final Connection conn;

    public CategoryDaoImpl(Connection conn) {
        this.conn = conn;
    }


    @Override
    public List<Category> queryAll() throws SQLException {
        List<Category> categories = new ArrayList<>();
        PreparedStatement smt = null;
        ResultSet rs = null;
        try {
            String sql = "select categoryId, categoryName, inIndex, sizeOnIndex, status, description\n" +
                    "from Category;";
            smt = conn.prepareStatement(sql);
            rs = smt.executeQuery();
            while (rs.next()) {
                Category category = CategoryBuilder.aCategory()
                        .withCategoryId(rs.getInt("categoryId"))
                        .withCategoryName(rs.getString("categoryName"))
                        .withDescription(rs.getString("description"))
                        .withSizeOnIndex(rs.getInt("sizeOnIndex"))
                        .withInIndex(rs.getBoolean("inIndex"))
                        .withStatus(rs.getBoolean("status"))
                        .build();
                categories.add(category);
            }
        } finally {
            close(conn, smt, rs);
        }

        return categories;
    }

    @Override
    public List<Category> queryAllCategoryOnIndex() throws SQLException {
        List<Category> categories = new ArrayList<>();
        PreparedStatement smt = null;
        ResultSet rs = null;
        try {
            String sql = "select categoryId, categoryName, sizeOnIndex\n" +
                    "from Category\n" +
                    "where inIndex = 1;";
            smt = conn.prepareStatement(sql);
            rs = smt.executeQuery();
            while (rs.next()) {
                Category category = CategoryBuilder.aCategory()
                        .withCategoryId(rs.getInt("categoryId"))
                        .withCategoryName(rs.getString("categoryName"))
                        .withSizeOnIndex(rs.getInt("sizeOnIndex"))
                        .build();
                categories.add(category);
            }
        } finally {
            close(conn, smt, rs);
        }

        return categories;
    }

    @Override
    public Category queryById(Integer categoryId) throws SQLException {
        PreparedStatement smt = null;
        ResultSet rs = null;
        try {
            String sql = "select top(1) categoryId, categoryName, inIndex, sizeOnIndex, status from Category where categoryId = ?;";
            smt = conn.prepareStatement(sql);
            smt.setInt(1, categoryId);
            rs = smt.executeQuery();
            if (rs.next()) {
                return CategoryBuilder.aCategory()
                        .withCategoryId(rs.getInt("categoryId"))
                        .withCategoryName(rs.getString("categoryName"))
                        .withInIndex(rs.getBoolean("inIndex"))
                        .withSizeOnIndex(rs.getInt("sizeOnIndex"))
                        .withStatus(rs.getBoolean("status"))
                        .build();
            }
        } finally {
            close(conn, smt, rs);
        }

        return null;
    }
}
