package com.bac.models.daos.impls;

import com.bac.models.daos.PaypalDao;
import com.bac.models.entities.PaypalPay;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaypalDaoImpl implements PaypalDao {
    private final Connection conn;

    public PaypalDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public PaypalPay insert(PaypalPay paypalPay) throws SQLException {
        PreparedStatement smt = null;
        ResultSet rs = null;
        try {
            String sql = "insert into PaypalPay (invoiceId, transactionId) values (?, ?);";
            smt = conn.prepareStatement(sql);
            smt.setInt(1, paypalPay.getInvoiceId());
            smt.setString(2, paypalPay.getTransactionId());
            int result = smt.executeUpdate();
            if (result == 1) {
                return paypalPay;
            }
        } finally {
            close(conn, smt, rs);
        }

        return null;
    }
}
