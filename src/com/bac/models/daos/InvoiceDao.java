package com.bac.models.daos;

import com.bac.models.entities.Invoice;

import java.sql.SQLException;

public interface InvoiceDao extends Dao {
    Invoice insert(Invoice invoice) throws SQLException;
}
