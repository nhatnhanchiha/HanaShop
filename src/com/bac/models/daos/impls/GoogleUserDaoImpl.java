package com.bac.models.daos.impls;

import com.bac.models.daos.GoogleUserDao;
import com.bac.models.entities.Account;
import com.bac.models.entities.GoogleUser;
import com.bac.models.entities.builder.AccountBuilder;
import com.bac.models.entities.builder.GoogleUserBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GoogleUserDaoImpl implements GoogleUserDao {
    private final Connection conn;

    public GoogleUserDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public GoogleUser getByGmail(String gmail) throws SQLException {
        PreparedStatement smt = null;
        ResultSet rs = null;
        try {
            String sql = "select top(1) gmail, A.username, A.firstName,A.status\n" +
                    "from GoogleUser\n" +
                    "join Account A on GoogleUser.username = A.username\n" +
                    "where gmail = ?;";
            smt = conn.prepareStatement(sql);
            smt.setString(1, gmail);
            rs =  smt.executeQuery();
            if (rs.next()) {
                GoogleUser googleUser = GoogleUserBuilder.aGoogleUser()
                        .withGmail(rs.getString("gmail"))
                        .withUsername(rs.getString("username"))
                        .build();
                Account account = AccountBuilder.anAccount()
                        .withFirstName(rs.getString("firstName"))
                        .withStatus(rs.getBoolean("status"))
                        .build();
                googleUser.setAccount(account);
                return googleUser;
            }
        } finally {
            close(conn, smt, rs);
        }
        return null;
    }

    @Override
    public GoogleUser addUser(GoogleUser googleUser) throws SQLException {
        PreparedStatement smt = null;
        ResultSet rs = null;
        try {
            String sql = "insert into GoogleUser (gmail, username) values (?, ?);";
            smt = conn.prepareStatement(sql);
            smt.setString(1, googleUser.getGmail());
            smt.setString(2, googleUser.getUsername());
            int result = smt.executeUpdate();
            if (result == 1) {
                return googleUser;
            }
        } finally {
            close(conn, smt, rs);
        }
        return null;
    }

}
