package com.bac.models.daos.impls;

import com.bac.models.daos.InvoiceDetailDao;
import com.bac.models.entities.InvoiceDetail;
import com.bac.models.entities.builder.InvoiceDetailBuilder;
import com.bac.models.entities.builder.ProductBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nhatn
 */
public class InvoiceDetailDaoImpl implements InvoiceDetailDao {
    private final Connection conn;

    public InvoiceDetailDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public int insertListInvoiceDetails(List<InvoiceDetail> invoiceDetails, Integer invoiceId) throws SQLException {
        PreparedStatement smt = null;
        ResultSet rs = null;
        int result = 0;
        try {
            String sql = "insert into InvoiceDetail (idInvoice, idProduct, price, quantity) values (?, ?, ?, ?);";
            smt = conn.prepareStatement(sql);
            for (InvoiceDetail invoiceDetail : invoiceDetails) {
                smt.setInt(1, invoiceId);
                smt.setInt(2, invoiceDetail.getIdProduct());
                smt.setDouble(3, invoiceDetail.getPrice());
                smt.setInt(4, invoiceDetail.getQuantity());
                result += smt.executeUpdate();
            }
        } finally {
            close(conn, smt, rs);
        }
        return result;
    }

    @Override
    public List<InvoiceDetail> queryInvoiceDetails(Integer invoiceId) throws SQLException {
        List<InvoiceDetail> invoiceDetails = new ArrayList<>();
        PreparedStatement smt = null;
        ResultSet rs = null;
        try {
            String sql = "select idProduct, I.price, I.quantity, P.name\n" +
                    "from InvoiceDetail I\n" +
                    "join Product P on I.idProduct = P.productId\n" +
                    "where idInvoice = ?;";
            smt = conn.prepareStatement(sql);
            smt.setInt(1, invoiceId);
            rs = smt.executeQuery();
            while (rs.next()) {
                InvoiceDetail invoiceDetail = InvoiceDetailBuilder.anInvoiceDetail()
                        .withIdProduct(rs.getInt("idProduct"))
                        .withPrice(rs.getDouble("price"))
                        .withQuantity(rs.getInt("quantity"))
                        .withProduct(ProductBuilder.aProduct().withName(rs.getString("name")).build())
                        .build();
                invoiceDetails.add(invoiceDetail);
            }
        } finally {
            close(conn, smt, rs);
        }
        return invoiceDetails;
    }


}
