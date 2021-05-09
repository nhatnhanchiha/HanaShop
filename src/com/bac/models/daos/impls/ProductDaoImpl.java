package com.bac.models.daos.impls;

import com.bac.models.daos.ProductDao;
import com.bac.models.entities.Product;
import com.bac.models.entities.builder.ProductBuilder;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author nhatn
 */
public class ProductDaoImpl implements ProductDao {
    private final Connection conn;

    public ProductDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Product> queryAll(int categoryId, int status, int limit, int offset) throws SQLException {
        List<Product> products = new ArrayList<>(limit);
        PreparedStatement smt = null;
        ResultSet rs = null;
        try {
            String categoryQueryString = "";
            if (categoryId != 0) {
                categoryQueryString = " where Product.categoryId = " + categoryId;
            }

            String statusQueryString = "";
            if (status == 1) {
                statusQueryString = categoryQueryString.isEmpty() ? "where Product.status = 1\n" : " and Product.status = 1\n";
            } else if (status == 2) {
                statusQueryString = categoryQueryString.isEmpty() ? "where Product.status = 0\n" : " and Product.status = 0\n";
            }
            String sql = "select productId, name, shortDescription, C.categoryId, imageUrl, Product.status, longDescription, price\n" +
                    "from Product\n" +
                    "join Category C on Product.categoryId = C.categoryId\n" +
                    categoryQueryString + statusQueryString +
                    "ORDER BY productId\n" +
                    "OFFSET ? ROWS\n" +
                    "FETCH NEXT ? ROWS ONLY;";
            smt = conn.prepareStatement(sql);
            smt.setInt(1, offset);
            smt.setInt(2, limit);
            rs = smt.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("productId"));
                product.setName(rs.getString("name"));
                product.setShortDescription(rs.getString("shortDescription"));
                product.setImageUrl(rs.getString("imageUrl"));
                product.setStatus(rs.getBoolean("status"));
                product.setLongDescription(rs.getString("longDescription"));
                product.setPrice(rs.getDouble("price"));
                product.setCategoryId(rs.getInt("categoryId"));
                products.add(product);
            }
        } finally {
            close(conn, smt, rs);
        }
        return products;
    }

    @Override
    public List<Product> queryByCategoryId(Integer categoryId, int limit, int offset) throws SQLException {
        List<Product> products = new ArrayList<>(limit);
        PreparedStatement smt = null;
        ResultSet rs = null;
        try {
            String sql = "select productId, name, shortDescription, imageUrl, price\n" +
                    "from Product\n" +
                    "where categoryId = ? and status = 1 and quantity > 0\n" +
                    "ORDER BY createDate desc\n" +
                    "OFFSET ? ROWS\n" +
                    "FETCH NEXT ? ROWS ONLY;";
            smt = conn.prepareStatement(sql);
            smt.setInt(1, categoryId);
            smt.setInt(2, offset);
            smt.setInt(3, limit);
            rs = smt.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("productId"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setShortDescription(rs.getString("shortDescription"));
                product.setImageUrl(rs.getString("imageUrl"));
                products.add(product);
            }
        } finally {
            close(conn, smt, rs);
        }
        return products;
    }

    @Override
    public List<Product> queryHotListProductByCategoryId(Integer categoryId) throws SQLException {
        List<Product> products = new ArrayList<>();
        PreparedStatement smt = null;
        ResultSet rs = null;
        try {
            String sql = "select top(8) Product.productId, Product.name, Product.shortDescription," +
                    " Product.imageUrl, Product.price, C.categoryId\n" +
                    "from Product\n" +
                    "join Category C on Product.categoryId = C.categoryId\n" +
                    "where C.categoryId = ? and Product.status = 1 and Product.quantity > 0\n" +
                    "order by quantitySold desc ;";
            smt = conn.prepareStatement(sql);
            smt.setInt(1, categoryId);
            rs = smt.executeQuery();
            while (rs.next()) {
                Product product = ProductBuilder.aProduct()
                        .withProductId(rs.getInt("productId"))
                        .withName(rs.getString("name"))
                        .withShortDescription(rs.getString("shortDescription"))
                        .withPrice(rs.getDouble("price"))
                        .withCategoryId(rs.getInt("categoryId"))
                        .withImageUrl(rs.getString("imageUrl"))
                        .build();
                products.add(product);
            }
        } finally {
            close(conn, smt, rs);
        }
        return products;
    }

    @Override
    public List<Product> queryByName(String name, int limit, int offset) throws SQLException {
        List<Product> products = new ArrayList<>();
        PreparedStatement smt = null;
        ResultSet rs = null;
        try {
            String sql = "select productId, name, shortDescription, imageUrl," +
                    " longDescription, price, createDate," +
                    " quantitySold, categoryId, status from Product\n" +
                    "where name like ?;";
            smt = conn.prepareStatement(sql);
            smt.setString(1, '%' + name + '%');
            rs = smt.executeQuery();
            while (rs.next()) {
                Product product = ProductBuilder.aProduct()
                        .withProductId(rs.getInt("productId"))
                        .withName(rs.getString("name"))
                        .withShortDescription(rs.getString("shortDescription"))
                        .withImageUrl(rs.getString("imageUrl"))
                        .withLongDescription(rs.getString("longDescription"))
                        .withPrice(rs.getDouble("price"))
                        .withQuantitySold(rs.getInt("quantitySold"))
                        .withCategoryId(rs.getInt("categoryId"))
                        .withStatus(rs.getBoolean("status"))
                        .build();
                products.add(product);
            }
        } finally {
            close(conn, smt, rs);
        }
        return products;
    }

    @Override
    public List<Product> queryByNameOrCategoryIdOrRangeMoney(String name, Integer categoryId, double minPrice, double maxPrice, int limit, int offset) throws SQLException {
        List<Product> products = new ArrayList<>();
        PreparedStatement smt = null;
        ResultSet rs = null;
        try {
            String categoryQueryString = "";
            if (categoryId != 0) {
                categoryQueryString = " and C.categoryId = " + categoryId;
            }
            String sql = "select productId, name, shortDescription, imageUrl, longDescription, price, createDate, quantitySold, C.categoryId from Product\n" +
                    "join Category C on Product.categoryId = C.categoryId\n" +
                    "where name like ? and price >= ? and price <= ? and quantity > 0 and Product.status = 1" + categoryQueryString + "\n" +
                    "order by createDate desc \n" +
                    "offset ? rows\n" +
                    "fetch next ? rows only ;";
            smt = conn.prepareStatement(sql);
            smt.setString(1, '%' + name + '%');
            smt.setDouble(2, minPrice);
            smt.setDouble(3, maxPrice);
            smt.setInt(4, offset);
            smt.setInt(5, limit);
            rs = smt.executeQuery();
            while (rs.next()) {
                Product product = ProductBuilder.aProduct()
                        .withProductId(rs.getInt("productId"))
                        .withName(rs.getString("name"))
                        .withShortDescription(rs.getString("shortDescription"))
                        .withImageUrl(rs.getString("imageUrl"))
                        .withCreateDate(rs.getDate("createDate").toLocalDate())
                        .withCategoryId(rs.getInt("categoryId"))
                        .withPrice(rs.getDouble("price"))
                        .build();
                products.add(product);
            }
        } finally {
            close(conn, smt, rs);
        }
        return products;
    }

    @Override
    public Product queryById(Integer productId) throws SQLException {
        PreparedStatement smt = null;
        ResultSet rs = null;
        Product product = null;
        try {
            String sql = "select top(1) productId, name, shortDescription, categoryId, imageUrl, longDescription, price, status, quantity\n" +
                    "from Product\n" +
                    "where productId = ?;";
            smt = conn.prepareStatement(sql);
            smt.setInt(1, productId);
            rs = smt.executeQuery();
            if (rs.next()) {
                product = new Product();
                product.setProductId(rs.getInt("productId"));
                product.setName(rs.getString("name"));
                product.setShortDescription(rs.getString("shortDescription"));
                product.setImageUrl(rs.getString("imageUrl"));
                product.setCategoryId(rs.getInt("categoryId"));
                product.setLongDescription(rs.getString("longDescription"));
                product.setPrice(rs.getDouble("price"));
                product.setQuantity(rs.getInt("quantity"));
                product.setStatus(rs.getBoolean("status"));
            }
        } finally {
            close(conn, smt, rs);
        }

        return product;
    }

    @Override
    public Product delete(Integer productId) throws SQLException {
        PreparedStatement smt = null;
        ResultSet rs = null;

        try {
            String sql = "update Product set status = 0 where productId = ?;";
            smt = conn.prepareStatement(sql);
            smt.setInt(1, productId);
            int result = smt.executeUpdate();
            if (result != 0) {
                Product product = new Product();
                product.setProductId(productId);
                return product;
            } else {
                return null;
            }
        } finally {
            close(conn, smt, rs);
        }
    }

    @Override
    public Product update(Product product) throws SQLException {
        PreparedStatement smt = null;
        ResultSet rs = null;
        try {
            String imageUrlSql = "\n";
            if (product.getImageUrl() != null) {
                imageUrlSql = "imageUrl = '" + product.getImageUrl() + "',\n";
            }
            String sql = "update Product set name = ?,\n" +
                    "shortDescription = ?,\n" +
                    "longDescription = ?,\n" +
                    imageUrlSql +
                    "price = ?,\n" +
                    "categoryId = ?,\n" +
                    "status = ?,\n" +
                    "quantity = ?\n" +
                    "where productId = ?;";
            smt = conn.prepareStatement(sql);
            smt.setString(1, product.getName());
            smt.setString(2, product.getShortDescription());
            smt.setString(3, product.getLongDescription());
            smt.setDouble(4, product.getPrice());
            smt.setInt(5, product.getCategoryId());
            smt.setBoolean(6, product.getStatus());
            smt.setInt(7, product.getQuantity());
            smt.setInt(8, product.getProductId());
            int result = smt.executeUpdate();
            if (result != 0) {
                return product;
            }
        } finally {
            close(conn, smt, rs);
        }

        return null;
    }

    @Override
    public Product insert(Product product) throws SQLException {
        PreparedStatement smt = null;
        ResultSet rs = null;
        try {
            String sql = "set nocount on;\n" +
                    "insert into Product (name, shortDescription, categoryId, imageUrl, longDescription, price, createDate, quantity, status)\n" +
                    "values (?, ?, ?, ?, ?, ?, ?, ?, ?)\n" +
                    "select productId\n" +
                    "FROM Product\n" +
                    "where @@ROWCOUNT = 1 and productId = scope_identity();\n;";
            smt = conn.prepareStatement(sql);
            smt.setString(1, product.getName());
            smt.setString(2, product.getShortDescription());
            smt.setInt(3, product.getCategoryId());
            smt.setString(4, product.getImageUrl());
            smt.setString(5, product.getLongDescription());
            smt.setDouble(6, product.getPrice());
            smt.setDate(7, Date.valueOf(LocalDate.now()));
            smt.setInt(8, product.getQuantity());
            smt.setBoolean(9, product.getStatus());
            rs = smt.executeQuery();
            if (rs.next()) {
                product.setProductId(rs.getInt("productId"));
                return product;
            }
        } finally {
            close(conn, smt, rs);
        }

        return null;
    }

    @Override
    public Product updateCategory(Integer productId, Integer categoryId) throws SQLException {
        PreparedStatement smt = null;
        ResultSet rs = null;
        try {
            String sql = "update Product set categoryId = ? where productId = ?;";
            smt = conn.prepareStatement(sql);
            smt.setInt(1, categoryId);
            smt.setInt(2, productId);
            int result = smt.executeUpdate();
            if (result != 0) {
                return new Product();
            }
        } finally {
            close(conn, smt, rs);
        }

        return null;
    }

    @Override
    public Product updateStatus(Integer productId, int status) throws SQLException {
        PreparedStatement smt = null;
        ResultSet rs = null;
        try {
            String sql = "update Product set status = ? where productId = ?;";
            smt = conn.prepareStatement(sql);
            smt.setInt(1, status);
            smt.setInt(2, productId);
            int result = smt.executeUpdate();
            if (result != 0) {
                return new Product();
            }
        } finally {
            close(conn, smt, rs);
        }
        return null;
    }

    @Override
    public boolean updateQuantity(Map<Product, Integer> quantitiesMap) throws SQLException {
        PreparedStatement smt = null;
        ResultSet rs = null;
        try {
            String sql = "update Product\n" +
                    "set quantity = quantity - ?, quantitySold = quantitySold + ?\n" +
                    "where productId = ? and quantity - Product.quantity >= 0;";
            smt = conn.prepareStatement(sql);
            for (Map.Entry<Product, Integer> e : quantitiesMap.entrySet()) {
                smt.setInt(1, e.getValue());
                smt.setInt(2, e.getValue());
                smt.setInt(3, e.getKey().getProductId());
                int result = smt.executeUpdate();
                if (result != 1) {
                    return false;
                }
            }
        } finally {
            close(conn, smt, rs);
        }
        return true;
    }

    @Override
    public List<Product> queryListFavoriteByUsername(String username) throws SQLException {
        List<Product> products = new ArrayList<>();
        PreparedStatement smt = null;
        ResultSet rs = null;
        try {
            String sql = "select top(8) idProduct, P.name, P.shortDescription, sum(InvoiceDetail.quantity) as quantity\n" +
                    "from InvoiceDetail\n" +
                    "join Invoice I on I.id = InvoiceDetail.idInvoice\n" +
                    "join Account A on A.username = I.username\n" +
                    "join Product P on InvoiceDetail.idProduct = P.productId\n" +
                    "where A.username = ?\n" +
                    "group by idProduct, P.name, P.shortDescription\n" +
                    "order by quantity desc";
            smt = conn.prepareStatement(sql);
            smt.setString(1, username);
            rs = smt.executeQuery();
            while (rs.next()) {
                Product product = ProductBuilder.aProduct()
                        .withProductId(rs.getInt("idProduct"))
                        .withName(rs.getString("name"))
                        .withShortDescription(rs.getString("shortDescription"))
                        .build();
                products.add(product);
            }
        } finally {
            close(conn, smt, rs);
        }

        return products;
    }

    @Override
    public List<Product> queryListOrderTogether(Integer productId) throws SQLException {
        List<Product> products = new ArrayList<>();
        PreparedStatement smt = null;
        ResultSet rs = null;
        try {
            String sql = "select top (8) idProduct,\n" +
                    "               P.name,\n" +
                    "               P.shortDescription,\n" +
                    "               P.imageUrl,\n" +
                    "               P.price,\n" +
                    "               sum(InvoiceDetail.quantity) as quantity\n" +
                    "from InvoiceDetail\n" +
                    "         join Invoice I on I.id = InvoiceDetail.idInvoice\n" +
                    "         join Product P on InvoiceDetail.idProduct = P.productId\n" +
                    "where I.id in (select top (10) Invoice.id\n" +
                    "               from Invoice\n" +
                    "                        join InvoiceDetail ID on Invoice.id = ID.idInvoice\n" +
                    "                        join Product P2 on ID.idProduct = P2.productId\n" +
                    "               where idProduct = ?\n" +
                    "               order by id desc)\n" +
                    "  and idProduct <> ?\n" +
                    "  and status = 1\n" +
                    "group by idProduct, P.name, P.shortDescription, P.imageUrl, P.price\n" +
                    "order by quantity desc;";
            smt = conn.prepareStatement(sql);
            smt.setInt(1, productId);
            smt.setInt(2, productId);
            rs = smt.executeQuery();
            while (rs.next()) {
                Product product = ProductBuilder.aProduct()
                        .withProductId(rs.getInt("idProduct"))
                        .withName(rs.getString("name"))
                        .withImageUrl(rs.getString("imageUrl"))
                        .withPrice(rs.getDouble("price"))
                        .withShortDescription(rs.getString("shortDescription"))
                        .build();
                products.add(product);
            }
        } finally {
            close(conn, smt, rs);
        }
        return products;
    }

    @Override
    public List<Product> queryListFavoriteOfHanaShop() throws SQLException {
        List<Product> products = new ArrayList<>();
        PreparedStatement smt = null;
        ResultSet rs = null;
        try {
            String sql = "select top(8) productId, name, shortDescription ,sum(ID.quantity) as quantity\n" +
                    "from Product\n" +
                    "join InvoiceDetail ID on Product.productId = ID.idProduct\n" +
                    "where status = 1\n" +
                    "group by productId, name, shortDescription\n" +
                    "order by quantity desc ;";
            smt = conn.prepareStatement(sql);
            rs = smt.executeQuery();
            while (rs.next()) {
                Product product = ProductBuilder.aProduct()
                        .withProductId(rs.getInt("productId"))
                        .withName(rs.getString("name"))
                        .withShortDescription(rs.getString("shortDescription"))
                        .build();
                products.add(product);
            }
        } finally {
            close(conn, smt, rs);
        }
        return products;
    }
}
