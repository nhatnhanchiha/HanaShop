package com.bac.models.services;

import com.bac.models.entities.PaypalPay;

import java.sql.SQLException;

public interface PaypalService {
    PaypalPay save(PaypalPay paypalPay) throws SQLException;
}
