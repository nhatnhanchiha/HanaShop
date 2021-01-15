package com.bac.models.daos.impls;

import com.bac.models.daos.AdminDao;
import com.bac.models.entities.Admin;
import com.bac.models.entities.builder.AdminBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDaoImpl implements AdminDao {
    private final Connection conn;

    public AdminDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Admin queryByUsername(String username) throws SQLException {
        PreparedStatement smt = null;
        ResultSet rs = null;
        try {
            String sql = "select top(1) username, password, name\n" +
                    "from Admin\n" +
                    "where username = ?;";
            smt = conn.prepareStatement(sql);
            smt.setString(1, username);
            rs = smt.executeQuery();
            if (rs.next()) {
                return AdminBuilder.anAdmin()
                        .withUsername(rs.getString("username"))
                        .withName(rs.getString("name"))
                        .build();
            }
        } finally {
            close(conn, smt, rs);
        }

        return null;
    }

    @Override
    public Admin queryByUsernameAndPassword(String username, String password) throws SQLException {
        PreparedStatement smt = null;
        ResultSet rs = null;
        try {
            String sql = "select top(1) username, name\n" +
                    "from Admin\n" +
                    "where username = ? and password = ?;";
            smt = conn.prepareStatement(sql);
            smt.setString(1, username);
            smt.setString(2, password);
            rs = smt.executeQuery();
            if (rs.next()) {
                return AdminBuilder.anAdmin()
                        .withUsername(rs.getString("username"))
                        .withName(rs.getString("name"))
                        .build();
            }
        } finally {
            close(conn, smt, rs);
        }

        return null;
    }
}
