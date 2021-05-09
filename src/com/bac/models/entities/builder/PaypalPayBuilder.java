package com.bac.models.entities.builder;

import com.bac.models.entities.PaypalPay;

public final class PaypalPayBuilder {
    private Integer invoiceId;
    private String transactionId;

    private PaypalPayBuilder() {
    }

    public static PaypalPayBuilder aPaypalPay() {
        return new PaypalPayBuilder();
    }

    public PaypalPayBuilder withInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
        return this;
    }

    public PaypalPayBuilder withTransactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public PaypalPay build() {
        PaypalPay paypalPay = new PaypalPay();
        paypalPay.setInvoiceId(invoiceId);
        paypalPay.setTransactionId(transactionId);
        return paypalPay;
    }
}
