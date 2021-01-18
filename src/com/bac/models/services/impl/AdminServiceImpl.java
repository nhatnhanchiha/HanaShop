package com.bac.models.services.impl;

import com.bac.models.daos.AdminDao;
import com.bac.models.entities.Admin;
import com.bac.models.services.AdminService;
import com.bac.models.utilities.HanaShopContext;

import java.sql.SQLException;

/**
 * @author nhatn
 */
public class AdminServiceImpl implements AdminService {
    private final HanaShopContext hanaShopContext;

    public AdminServiceImpl(HanaShopContext hanaShopContext) {
        this.hanaShopContext = hanaShopContext;
    }

    @Override
    public Admin login(String username, String password) throws SQLException {
        AdminDao adminDao = hanaShopContext.getAdminDao();
        return adminDao.queryByUsernameAndPassword(username, password);
    }
}
