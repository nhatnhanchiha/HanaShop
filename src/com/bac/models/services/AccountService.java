package com.bac.models.services;

import com.bac.models.entities.Account;

import java.sql.SQLException;

public interface AccountService {
    Account register(Account account) throws SQLException;
    Account login(String username, String password) throws SQLException;
}
