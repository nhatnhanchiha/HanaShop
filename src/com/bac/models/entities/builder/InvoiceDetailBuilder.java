package com.bac.models.entities.builder;

import com.bac.models.entities.InvoiceDetail;
import com.bac.models.entities.Product;

public final class InvoiceDetailBuilder {
    private Integer idInvoice;
    private Integer idProduct;
    private Double price;
    private Integer quantity;
    private Product product;

    private InvoiceDetailBuilder() {
    }

    public static InvoiceDetailBuilder anInvoiceDetail() {
        return new InvoiceDetailBuilder();
    }

    public InvoiceDetailBuilder withIdInvoice(Integer idInvoice) {
        this.idInvoice = idInvoice;
        return this;
    }

    public InvoiceDetailBuilder withIdProduct(Integer idProduct) {
        this.idProduct = idProduct;
        return this;
    }

    public InvoiceDetailBuilder withPrice(Double price) {
        this.price = price;
        return this;
    }

    public InvoiceDetailBuilder withQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public InvoiceDetailBuilder withProduct(Product product) {
        this.product = product;
        return this;
    }

    public InvoiceDetail build() {
        InvoiceDetail invoiceDetail = new InvoiceDetail();
        invoiceDetail.setIdInvoice(idInvoice);
        invoiceDetail.setIdProduct(idProduct);
        invoiceDetail.setPrice(price);
        invoiceDetail.setQuantity(quantity);
        invoiceDetail.setProduct(product);
        return invoiceDetail;
    }
}
