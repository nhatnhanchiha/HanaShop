package com.bac.models.pages;

import com.bac.models.entities.Invoice;
import com.bac.models.entities.InvoiceDetail;

import java.io.Serializable;
import java.util.List;

public class InvoicePage implements Serializable {
    private static final long serialVersionUID = 1299053460084131324L;

    private Invoice invoice;
    private List<InvoiceDetail> invoiceDetails;
    private double sum;
    private String fullAddress;

    public InvoicePage(Invoice invoice, List<InvoiceDetail> invoiceDetails) {
        this.invoice = invoice;
        this.invoiceDetails = invoiceDetails;
        for (InvoiceDetail invoiceDetail : invoiceDetails) {
            sum += invoiceDetail.getPrice() * invoiceDetail.getQuantity();
        }

        sum = Math.round(sum * 100) / 100.0;
        fullAddress = invoice.getAddressLine()
                + " " + invoice.getBlock()
                + " " + invoice.getDistrict()
                + " " + invoice.getProvince();
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public List<InvoiceDetail> getInvoiceDetails() {
        return invoiceDetails;
    }

    public void setInvoiceDetails(List<InvoiceDetail> invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }
}
