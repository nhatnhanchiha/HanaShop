package com.bac.models.services.impl;

import com.bac.models.daos.InvoiceDao;
import com.bac.models.entities.Invoice;
import com.bac.models.services.InvoiceService;
import com.bac.models.utilities.HanaShopContext;

import java.sql.SQLException;
import java.util.List;

public class InvoiceServiceImpl implements InvoiceService {
    private final HanaShopContext hanaShopContext;

    public InvoiceServiceImpl(HanaShopContext hanaShopContext) {
        this.hanaShopContext = hanaShopContext;
    }

    @Override
    public List<Invoice> queryAll(int limit, int offset) {
        return null;
    }

    @Override
    public Invoice createAnInvoice(Invoice invoice) throws SQLException {
        InvoiceDao invoiceDao = hanaShopContext.getInvoiceDao();
        return invoiceDao.insert(invoice);
    }
}
