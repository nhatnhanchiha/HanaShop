package com.bac.models.daos;

import com.bac.models.entities.InvoiceDetail;

import java.sql.SQLException;
import java.util.List;

/**
 * @author nhatn
 */
public interface InvoiceDetailDao extends Dao {
    int insertListInvoiceDetails(List<InvoiceDetail> invoiceDetails, Integer invoiceId) throws SQLException;
    List<InvoiceDetail> queryInvoiceDetails(Integer invoiceId) throws SQLException;
}
