package com.bac.models.daos.impls;

import com.bac.models.daos.LogSqlDao;
import com.bac.models.entities.LogSql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * @author nhatn
 */
public class LogSqlDaoImpl implements LogSqlDao {
    private final Connection conn;

    public LogSqlDaoImpl(Connection conn) {
        this.conn = conn;
    }

/*    @Override
    public List<LogSql> queryAll(int limit, int offset) throws SQLException {
        List<LogSql> logSqls = new ArrayList<>();
        PreparedStatement smt = null;
        ResultSet rs = null;
        try {
            String sql = "select logSqlId, type, productId, updatedDate, updatedUser\n" +
                    "from LogSql\n" +
                    "ORDER BY logSqlId DESC\n" +
                    "OFFSET ? ROWS\n" +
                    "FETCH NEXT ? ROWS ONLY;";
            smt = conn.prepareStatement(sql);
            smt.setInt(1, offset);
            smt.setInt(2, limit);
            rs = smt.executeQuery();
            while (rs.next()) {
                LogSql logSql = new LogSql();
                logSql.setId(rs.getInt("logSqlId"));
                logSql.setType(LogSql.Type.values()[rs.getInt("type")]);
                logSql.setProductId(rs.getInt("productId"));
                logSql.setUpdatedDate(rs.getTimestamp("updatedDate").toLocalDateTime());
                logSql.setUpdatedUser(rs.getString("updatedUser"));
                logSqls.add(logSql);
            }
        } finally {
            close(conn, smt, rs);
        }

        return logSqls;
    }*/

    @Override
    public LogSql insert(LogSql logSql) throws SQLException {
        PreparedStatement smt = null;
        ResultSet rs = null;
        try {
            String sql = "insert into LogSql (type, productId, updatedDate, updatedUser)\n" +
                    "values (?, ?, ?, ?);";
            smt = conn.prepareStatement(sql);
            smt.setInt(1, logSql.getType().ordinal());
            smt.setInt(2, logSql.getProductId());
            smt.setTimestamp(3, Timestamp.valueOf(logSql.getUpdatedDate()));
            smt.setString(4, logSql.getUpdatedUser());
            int result = smt.executeUpdate();
            if (result != 0) {
                return logSql;
            }
        } finally {
            close(conn, smt, rs);
        }

        return null;
    }
}
