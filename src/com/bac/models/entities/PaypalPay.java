package com.bac.models.entities;

import java.io.Serializable;

/**
 * @author nhatn
 */
public class PaypalPay implements Serializable {
    private static final long serialVersionUID = 8536070034930476273L;

    private Integer invoiceId;
    private String transactionId;

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}