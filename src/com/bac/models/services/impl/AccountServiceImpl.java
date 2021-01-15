package com.bac.models.services.impl;

import com.bac.models.daos.AccountDao;
import com.bac.models.entities.Account;
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
}
