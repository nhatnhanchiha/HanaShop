package com.bac.models.services;

import com.bac.models.entities.Invoice;
import com.bac.models.entities.InvoiceDetail;

import java.sql.SQLException;
import java.util.List;

public interface InvoiceService {
    List<Invoice> queryAll(int limit, int offset);
    List<Invoice> getAllInvoice(String username, int limit, int offset) throws SQLException;
    Invoice createAnInvoice(Invoice invoice, List<InvoiceDetail> invoiceDetails) throws SQLException;
    Invoice getInvoiceByIdAndUsername(Integer invoiceId, String username) throws SQLException;
}
