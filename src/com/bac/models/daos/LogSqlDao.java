package com.bac.models.daos;

import com.bac.models.entities.LogSql;

import java.sql.SQLException;

public interface LogSqlDao extends Dao {
//    List<LogSql> queryAll(int limit, int offset) throws SQLException;
    LogSql insert(LogSql logSql) throws SQLException;
}