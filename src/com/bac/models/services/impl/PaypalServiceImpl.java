package com.bac.models.services.impl;

import com.bac.models.daos.PaypalDao;
import com.bac.models.entities.PaypalPay;
import com.bac.models.services.PaypalService;
import com.bac.models.utilities.HanaShopContext;

import java.sql.SQLException;

public class PaypalServiceImpl implements PaypalService {
    private final HanaShopContext hanaShopContext;

    public PaypalServiceImpl(HanaShopContext hanaShopContext) {
        this.hanaShopContext = hanaShopContext;
    }

    @Override
    public PaypalPay save(PaypalPay paypalPay) throws SQLException {
        PaypalDao paypalDao = hanaShopContext.getPaypalDao();
        return paypalDao.insert(paypalPay);
    }
}
