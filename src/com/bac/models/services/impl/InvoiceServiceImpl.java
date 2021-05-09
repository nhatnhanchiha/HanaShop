package com.bac.models.services.impl;

import com.bac.models.daos.InvoiceDao;
import com.bac.models.daos.InvoiceDetailDao;
import com.bac.models.entities.Invoice;
import com.bac.models.entities.InvoiceDetail;
import com.bac.models.services.InvoiceService;
import com.bac.models.utilities.HanaShopContext;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class InvoiceServiceImpl implements InvoiceService {
    private final HanaShopContext hanaShopContext;

    public InvoiceServiceImpl(HanaShopContext hanaShopContext) {
        this.hanaShopContext = hanaShopContext;
    }

/*    @Override
    public List<Invoice> queryAll(int limit, int offset) {
        return null;
    }*/

    @Override
    public List<Invoice> getAllInvoice(String username, String productName, LocalDate createdDate, int limit, int offset) throws SQLException {
        InvoiceDao invoiceDao = hanaShopContext.getInvoiceDao();
        return invoiceDao.queryByUsername(username, productName, createdDate, limit, offset);
    }

    @Override
    public Invoice createAnInvoice(Invoice invoice, List<InvoiceDetail> invoiceDetails) throws SQLException {
        InvoiceDao invoiceDao = hanaShopContext.getInvoiceDao();
        Invoice invoiceInDb = invoiceDao.insert(invoice);
        if (invoiceInDb != null) {
            InvoiceDetailDao invoiceDetailDao = hanaShopContext.getInvoiceDetailDao();
            int count = invoiceDetailDao.insertListInvoiceDetails(invoiceDetails, invoiceInDb.getId());
            if (count == invoiceDetails.size()) {
                return invoiceInDb;
            }
        }
        return null;
    }

    @Override
    public Invoice getInvoiceByIdAndUsername(Integer invoiceId, String username) throws SQLException {
        InvoiceDao invoiceDao = hanaShopContext.getInvoiceDao();
        return invoiceDao.queryByIdAndUsername(invoiceId, username);
    }
}