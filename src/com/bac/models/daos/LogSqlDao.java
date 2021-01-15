package com.bac.models.daos;

import com.bac.models.entities.LogSql;

import java.sql.SQLException;
import java.util.List;

public interface LogSqlDao extends Dao {
    List<LogSql> queryAll(int limit, int offset) throws SQLException;
    LogSql insert(LogSql logSql) throws SQLException;
}