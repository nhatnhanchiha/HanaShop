package com.bac.models.entities.builder;

import com.bac.models.entities.Invoice;

import java.time.LocalDate;

public final class InvoiceBuilder {
    private int id;
    private String username;
    private String addressLine;
    private String block;
    private String district;
    private String province;
    private String phoneNumber;
    private Boolean paid;
    private LocalDate createDate;
    private Boolean payWithPayPal;

    private InvoiceBuilder() {
    }

    public static InvoiceBuilder anInvoice() {
        return new InvoiceBuilder();
    }

    public InvoiceBuilder withId(int id) {
        this.id = id;
        return this;
    }

    public InvoiceBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public InvoiceBuilder withAddressLine(String addressLine) {
        this.addressLine = addressLine;
        return this;
    }

    public InvoiceBuilder withBlock(String block) {
        this.block = block;
        return this;
    }

    public InvoiceBuilder withDistrict(String district) {
        this.district = district;
        return this;
    }

    public InvoiceBuilder withProvince(String province) {
        this.province = province;
        return this;
    }

    public InvoiceBuilder withPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public InvoiceBuilder withPaid(Boolean paid) {
        this.paid = paid;
        return this;
    }

    public InvoiceBuilder withCreateDate(LocalDate createDate) {
        this.createDate = createDate;
        return this;
    }

    public InvoiceBuilder withPayWithPayPal(Boolean payWithPayPal) {
        this.payWithPayPal = payWithPayPal;
        return this;
    }

    public Invoice build() {
        Invoice invoice = new Invoice();
        invoice.setId(id);
        invoice.setUsername(username);
        invoice.setAddressLine(addressLine);
        invoice.setBlock(block);
        invoice.setDistrict(district);
        invoice.setProvince(province);
        invoice.setPhoneNumber(phoneNumber);
        invoice.setPaid(paid);
        invoice.setCreateDate(createDate);
        invoice.setPayWithPayPal(payWithPayPal);
        return invoice;
    }
}
