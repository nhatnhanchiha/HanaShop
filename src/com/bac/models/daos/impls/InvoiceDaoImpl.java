package com.bac.models.daos.impls;

import com.bac.models.daos.InvoiceDao;
import com.bac.models.entities.Invoice;
import com.bac.models.entities.builder.InvoiceBuilder;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nhatn
 */
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
                    "insert into Invoice (username, addressLine, block, district, province, phoneNumber, paid, createdDate, payWithPayPal)\n" +
                    "values (?, ?, ?, ?, ?, ?, ?, ?, ?)\n" +
                    "select id\n" +
                    "from Invoice\n" +
                    "where @@ROWCOUNT = 1 and id = scope_identity();";
            smt = conn.prepareStatement(sql);
            smt.setString(1, invoice.getUsername());
            smt.setString(2, invoice.getAddressLine());
            smt.setString(3, invoice.getBlock());
            smt.setString(4, invoice.getDistrict());
            smt.setString(5, invoice.getProvince());
            smt.setString(6, invoice.getPhoneNumber());
            smt.setBoolean(7, invoice.getPaid());
            smt.setDate(8, Date.valueOf(invoice.getCreateDate()));
            smt.setBoolean(9, invoice.getPayWithPayPal());
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

    @Override
    public List<Invoice> queryByUsername(String username, String productName, LocalDate createdDate, int limit, int offset) throws SQLException {
        List<Invoice> invoices = new ArrayList<>();
        PreparedStatement smt = null;
        ResultSet rs = null;
        String productNameSql = "";
        String productNameSql2 = "";
        String createdDateSql = "";
        try {
            if (productName != null && !productName.isEmpty()) {
                productNameSql = "join InvoiceDetail I on Invoice.id = I.idInvoice\n" +
                        "join Product P on I.idProduct = P.productId\n";
                productNameSql2 = " and P.name like ?";
            }

            if (createdDate != null) {
                createdDateSql = " and createdDate = ?\n";
            }

            String sql = "select id, createdDate, paid\n" +
                    "from Invoice\n" +
                    productNameSql + "\n" +
                    "where username = ? " + productNameSql2 + createdDateSql + "\n" +
                    "order by id desc \n" +
                    "offset ? rows\n" +
                    "fetch next ? rows only ;";
            smt = conn.prepareStatement(sql);
            int index = 1;
            smt.setString(index++, username);
            if (createdDate != null) {
                smt.setDate(index++, Date.valueOf(createdDate));
            }
            if (!productNameSql2.isEmpty()) {
                smt.setString(index++, '%' + productName + '%');
            }
            smt.setInt(index++, offset);
            smt.setInt(index, limit);
            rs = smt.executeQuery();
            while (rs.next()) {
                Invoice invoice = InvoiceBuilder.anInvoice()
                        .withId(rs.getInt("id"))
                        .withCreateDate(rs.getDate("createdDate").toLocalDate())
                        .withPaid(rs.getBoolean("paid"))
                        .build();
                invoices.add(invoice);
            }
        } finally {
            close(conn, smt, rs);
        }
        return invoices;
    }

    @Override
    public Invoice queryByIdAndUsername(Integer invoiceId, String username) throws SQLException {
        PreparedStatement smt = null;
        ResultSet rs = null;
        try {
            String sql = "select top(1) id, username, addressLine, block, district, phoneNumber, province, paid, createdDate\n" +
                    "from Invoice\n" +
                    "where id = ? and username = ?;";
            smt = conn.prepareStatement(sql);
            smt.setInt(1, invoiceId);
            smt.setString(2, username);
            rs = smt.executeQuery();
            if (rs.next()) {
                return InvoiceBuilder.anInvoice()
                        .withId(rs.getInt("id"))
                        .withUsername(rs.getString("username"))
                        .withAddressLine(rs.getString("addressLine"))
                        .withBlock(rs.getString("block"))
                        .withDistrict(rs.getString("district"))
                        .withProvince(rs.getString("province"))
                        .withPhoneNumber(rs.getString("phoneNumber"))
                        .withCreateDate(rs.getDate("createdDate").toLocalDate())
                        .withPaid(rs.getBoolean("paid"))
                        .build();
            }
        } finally {
            close(conn, smt, rs);
        }

        return null;
    }

    @Override
    public List<Invoice> queryByNameProductAndShoppingDate(String productName, LocalDate shoppingDate, int limit, int offset) throws SQLException {
        List<Invoice> invoices = new ArrayList<>();
        PreparedStatement smt = null;
        ResultSet rs = null;
        try {
            String sql = "select id, paid\n" +
                    "from Invoice\n" +
                    "join InvoiceDetail ID on Invoice.id = ID.idInvoice\n" +
                    "join Product P on P.productId = ID.idProduct\n" +
                    "where P.name like ? and createdDate = ?\n" +
                    "order by id desc \n" +
                    "offset ? rows\n" +
                    "fetch next ? rows only";
            smt = conn.prepareStatement(sql);
            smt.setString(1, '%' + productName + '%');
            smt.setDate(2, Date.valueOf(shoppingDate));
            smt.setInt(3, offset);
            smt.setInt(4, limit);
            rs = smt.executeQuery();
            if (rs.next()) {
                Invoice invoice = InvoiceBuilder.anInvoice()
                        .withId(rs.getInt("id"))
                        .withPaid(rs.getBoolean("paid"))
                        .build();
                invoices.add(invoice);
            }
        } finally {
            close(conn, smt, rs);
        }

        return invoices;
    }


}
