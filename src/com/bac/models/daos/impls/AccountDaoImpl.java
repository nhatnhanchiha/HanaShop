package com.bac.models.daos.impls;

import com.bac.models.daos.AccountDao;
import com.bac.models.entities.Account;
import com.bac.models.entities.builder.AccountBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author nhatn
 */
public class AccountDaoImpl implements AccountDao {
    private final Connection conn;

    public AccountDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Account queryByEmail(String email) throws SQLException {
        PreparedStatement smt = null;
        ResultSet rs = null;
        try {
            String sql = "select username, FirstName, LastName\n" +
                    "from Account\n" +
                    "where username = ? and Status = 1;";
            smt = conn.prepareStatement(sql);
            smt.setString(1, email);
            rs = smt.executeQuery();
            if (rs.next()) {
                return AccountBuilder
                        .anAccount()
                        .withUsername(rs.getString("username"))
                        .withFirstName(rs.getString("FirstName"))
                        .withLastName(rs.getString("LastName"))
                        .build();
            }
        } finally {
            close(conn, smt, rs);
        }
        return null;
    }

    @Override
    public Account addInfo(Account account) throws SQLException {
        PreparedStatement smt = null;
        ResultSet rs = null;
        try {
            String sql = "update Account set firstName = ?, lastName = ? where username = ?;";
            smt = conn.prepareStatement(sql);
            smt.setString(1, account.getFirstName());
            smt.setString(2, account.getLastName());
            smt.setString(3, account.getUsername());
            int result = smt.executeUpdate();
            if (result == 1) {
                return account;
            }
        } finally {
            close(conn, smt, rs);
        }
        return null;
    }

    @Override
    public Account queryByUsernameAndPassword(String username, String password) throws SQLException {
        PreparedStatement smt = null;
        ResultSet rs = null;
        try {
            String sql = "select username, FirstName, lastName, status\n" +
                    "from Account\n" +
                    "where username = ? and  Password = ?;";
            smt = conn.prepareStatement(sql);
            smt.setString(1, username);
            smt.setString(2, password);
            rs = smt.executeQuery();
            if (rs.next()) {
                return AccountBuilder
                        .anAccount()
                        .withUsername(rs.getString("username"))
                        .withFirstName(rs.getString("FirstName"))
                        .withLastName(rs.getString("lastName"))
                        .withStatus(rs.getBoolean("status"))
                        .build();
            }
        } finally {
            close(conn, smt, rs);
        }
        return null;
    }

    @Override
    public Account insert(Account account) throws SQLException {
        PreparedStatement smt = null;
        ResultSet rs = null;
        try {
            String sql = "insert into Account (username, Password, FirstName, LastName, Status) values (?, ?, ?, ?, 1);";
            smt = conn.prepareStatement(sql);
            smt.setString(1, account.getUsername());
            smt.setString(2, account.getPassword());
            smt.setString(3, account.getFirstName());
            smt.setString(4, account.getLastName());
            int result = smt.executeUpdate();
            if (result != 0) {
                return account;
            }
        } finally {
            close(conn, smt, rs);
        }

        return null;
    }
}
