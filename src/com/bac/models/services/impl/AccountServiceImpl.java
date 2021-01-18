package com.bac.models.services.impl;

import com.bac.models.daos.AccountDao;
import com.bac.models.daos.GoogleUserDao;
import com.bac.models.entities.Account;
import com.bac.models.entities.GoogleUser;
import com.bac.models.services.AccountService;
import com.bac.models.utilities.HanaShopContext;

import java.sql.SQLException;

/**
 * @author nhatn
 */
public class AccountServiceImpl implements AccountService {
    private final HanaShopContext hanaShopContext;

    public AccountServiceImpl(HanaShopContext hanaShopContext) {
        this.hanaShopContext = hanaShopContext;
    }

    // todo: need fix save change
    @Override
    public Account register(Account account) throws SQLException {
        AccountDao accountDao = hanaShopContext.getAccountDao();
        Account account1 = accountDao.queryByEmail(account.getUsername());
        if (account1 != null) {
            return null;
        }

        Account insertedAccount = accountDao.insert(account);
        if (insertedAccount == null) {
            hanaShopContext.rollback();
        } else {
            hanaShopContext.saveChanges();
        }
        return insertedAccount;
    }

    @Override
    public Account login(String username, String password) throws SQLException {
        AccountDao accountDao = hanaShopContext.getAccountDao();
        return accountDao.queryByUsernameAndPassword(username, password);
    }

    @Override
    public GoogleUser loginWithGoogle(String gmail) throws SQLException {
        GoogleUserDao googleUserDao = hanaShopContext.getGoogleUserDao();
        return googleUserDao.getByGmail(gmail);
    }

    @Override
    public GoogleUser addAGoogleUser(GoogleUser googleUser) throws SQLException {
        AccountDao accountDao = hanaShopContext.getAccountDao();
        Account account = accountDao.insert(googleUser.getAccount());
        if (account != null) {
            GoogleUserDao googleUserDao = hanaShopContext.getGoogleUserDao();
            return googleUserDao.addUser(googleUser);
        }
        return null;
    }

    @Override
    public Account addInfo(Account account) throws SQLException {
        AccountDao accountDao = hanaShopContext.getAccountDao();
        return accountDao.addInfo(account);
    }
}
