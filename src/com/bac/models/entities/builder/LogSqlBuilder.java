package com.bac.models.entities.builder;

import com.bac.models.entities.LogSql;

import java.time.LocalDateTime;

public final class LogSqlBuilder {
    private Integer id;
    private LogSql.Type type;
    private Integer productId;
    private LocalDateTime updatedDate;
    private String updatedUser;

    private LogSqlBuilder() {
    }

    public static LogSqlBuilder aLogSql() {
        return new LogSqlBuilder();
    }

    public LogSqlBuilder withId(Integer id) {
        this.id = id;
        return this;
    }

    public LogSqlBuilder withType(LogSql.Type type) {
        this.type = type;
        return this;
    }

    public LogSqlBuilder withProductId(Integer productId) {
        this.productId = productId;
        return this;
    }

    public LogSqlBuilder withUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public LogSqlBuilder withUpdatedUser(String updatedUser) {
        this.updatedUser = updatedUser;
        return this;
    }

    public LogSql build() {
        LogSql logSql = new LogSql();
        logSql.setId(id);
        logSql.setType(type);
        logSql.setProductId(productId);
        logSql.setUpdatedDate(updatedDate);
        logSql.setUpdatedUser(updatedUser);
        return logSql;
    }
}
