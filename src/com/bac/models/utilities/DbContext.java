package com.bac.models.utilities;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author nhatn
 */
public class DbContext {
    protected Connection connection;

    public DbContext(String contextName) throws SQLException, NamingException {
        Context currentContext = new InitialContext();
        Context tomcatContext = (Context) currentContext.lookup("java:comp/env");
        DataSource ds = (DataSource) tomcatContext.lookup(contextName);
        connection = ds.getConnection();
        connection.setAutoCommit(false);
    }

    public Connection getConnection() {
        return connection;
    }

    public DbContext updateMode() throws SQLException {
        if (connection != null && !connection.isClosed() && connection.getAutoCommit()) {
            connection.setAutoCommit(false);
        }
        return this;
    }

    public void rollback() throws SQLException {
        if (connection != null && !connection.isClosed() && !connection.getAutoCommit()) {
            connection.rollback();
        }
    }

    public void saveChanges() throws SQLException {
        if (connection != null && !connection.isClosed() && !connection.getAutoCommit()) {
            connection.commit();
        }
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
