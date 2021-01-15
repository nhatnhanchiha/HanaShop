package com.bac.models.daos.impls;

import com.bac.models.daos.InvoiceDao;
import com.bac.models.entities.Invoice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InvoiceDaoImpl implements InvoiceDao {
    private final Connection conn;

    public InvoiceDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Invoice insert(Invoice invoice) throws SQLException {
        PreparedStatement smt = null;
        ResultSet rs = null;
        try {
            String sql = "set nocount on;\n" +
                    "insert into Invoice (username, addressLine, block, district, province, phoneNumber)\n" +
                    "values (?, ?, ?, ?, ?, ?)\n" +
                    "select id\n" +
                    "from Invoice\n" +
                    "where @@ROWCOUNT = 1 and id = scope_identity();";
            smt = conn.prepareStatement(sql);
            smt.setString(1, invoice.getEmail());
            smt.setString(2, invoice.getAddressLine());
            smt.setString(3, invoice.getBlock());
            smt.setString(4, invoice.getDistrict());
            smt.setString(5, invoice.getProvince());
            smt.setString(6, invoice.getPhoneNumber());
            rs = smt.executeQuery();
            if (rs.next()) {
                invoice.setId(rs.getInt(1));
                return invoice;
            }
        } finally {
            close(conn, smt, rs);
        }
        return null;
    }
}
