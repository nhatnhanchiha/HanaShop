package com.bac.models.services;

import com.bac.models.entities.Admin;

import java.sql.SQLException;

public interface AdminService {
    Admin login(String username, String password) throws SQLException;
}
