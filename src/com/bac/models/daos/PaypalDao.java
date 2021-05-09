package com.bac.models.daos;

import com.bac.models.entities.PaypalPay;

import java.sql.SQLException;

public interface PaypalDao extends Dao {
    PaypalPay insert(PaypalPay paypalPay) throws SQLException;
}
