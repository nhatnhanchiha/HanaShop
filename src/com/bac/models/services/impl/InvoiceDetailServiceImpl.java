package com.bac.models.services.impl;

import com.bac.models.daos.InvoiceDetailDao;
import com.bac.models.entities.InvoiceDetail;
import com.bac.models.services.InvoiceDetailService;
import com.bac.models.utilities.HanaShopContext;

import java.sql.SQLException;
import java.util.List;

public class InvoiceDetailServiceImpl implements InvoiceDetailService {
    private final HanaShopContext hanaShopContext;

    public InvoiceDetailServiceImpl(HanaShopContext hanaShopContext) {
        this.hanaShopContext = hanaShopContext;
    }


    @Override
    public List<InvoiceDetail> getListInvoiceDetailByInvoiceId(Integer invoiceId) throws SQLException {
        InvoiceDetailDao invoiceDetailDao = hanaShopContext.getInvoiceDetailDao();
        return invoiceDetailDao.queryInvoiceDetails(invoiceId);
    }
}
