package com.bac.models.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author nhatn
 */
public interface Dao {
    /**
     * close Dao after query
     * @param conn Connection
     * @param smt PreparedStatement
     * @param rs ResultSet
     * @throws SQLException need to be handled
     */
    default void close(Connection conn, PreparedStatement smt, ResultSet rs) throws SQLException {
        if (rs != null && !rs.isClosed()) {
            rs.close();
        }

        if (smt != null && !smt.isClosed()) {
            smt.close();
        }

        if (conn != null && !conn.isClosed() && conn.getAutoCommit()) {
            conn.close();
        }
    }
}
