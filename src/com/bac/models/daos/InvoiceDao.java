package com.bac.models.daos;

import com.bac.models.entities.Invoice;

import java.sql.SQLException;
import java.util.List;

public interface InvoiceDao extends Dao {
    Invoice insert(Invoice invoice) throws SQLException;
    List<Invoice> queryByUsername(String username, int limit, int offset) throws SQLException;
    Invoice queryByIdAndUsername(Integer invoiceId, String username) throws SQLException;
}