package com.bac.models.services;

import com.bac.models.entities.Account;
import com.bac.models.entities.GoogleUser;

import java.sql.SQLException;

/**
 * @author nhatn
 */
public interface AccountService {
    Account register(Account account) throws SQLException;
    Account login(String username, String password) throws SQLException;
    GoogleUser loginWithGoogle(String gmail) throws SQLException;
    GoogleUser addAGoogleUser(GoogleUser googleUser) throws SQLException;
    Account addInfo(Account account) throws SQLException;
}
