package com.bac.models.daos;

import com.bac.models.entities.Admin;

import java.sql.SQLException;

public interface AdminDao extends Dao {
    Admin queryByUsername(String username) throws SQLException;
    Admin queryByUsernameAndPassword(String username, String password) throws SQLException;
}
