package com.bac.models.services;

import com.bac.models.entities.InvoiceDetail;

import java.sql.SQLException;
import java.util.List;

public interface InvoiceDetailService {
    List<InvoiceDetail> getListInvoiceDetailByInvoiceId(Integer invoiceId) throws SQLException;
}
