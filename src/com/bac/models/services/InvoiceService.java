package com.bac.models.services;

import com.bac.models.entities.Invoice;

import java.sql.SQLException;
import java.util.List;

public interface InvoiceService {
    List<Invoice> queryAll(int limit, int offset);
    Invoice createAnInvoice(Invoice invoice) throws SQLException;
}
