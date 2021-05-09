package com.bac.models.services;

import com.bac.models.entities.Invoice;
import com.bac.models.entities.InvoiceDetail;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface InvoiceService {
//    List<Invoice> queryAll(int limit, int offset);
    List<Invoice> getAllInvoice(String username, String productName, LocalDate createdDate, int limit, int offset) throws SQLException;
    Invoice createAnInvoice(Invoice invoice, List<InvoiceDetail> invoiceDetails) throws SQLException;
    Invoice getInvoiceByIdAndUsername(Integer invoiceId, String username) throws SQLException;
}
