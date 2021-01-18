package com.bac.models.daos;

import com.bac.models.entities.Account;

import java.sql.SQLException;

public interface AccountDao extends Dao {
    Account queryByUsernameAndPassword(String username, String password) throws SQLException;
    Account insert(Account account) throws SQLException;
    Account queryByEmail(String email) throws SQLException;
    Account addInfo(Account account) throws SQLException;
}
