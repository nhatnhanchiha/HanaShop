package com.bac.models.entities.builder;

import com.bac.models.entities.Invoice;

public final class InvoiceBuilder {
    private int id;
    private String email;
    private String addressLine;
    private String block;
    private String district;
    private String province;
    private String phoneNumber;

    private InvoiceBuilder() {
    }

    public static InvoiceBuilder anInvoice() {
        return new InvoiceBuilder();
    }

    public InvoiceBuilder withId(int id) {
        this.id = id;
        return this;
    }

    public InvoiceBuilder withEmail(String email) {
        this.email = email;
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

    public Invoice build() {
        Invoice invoice = new Invoice();
        invoice.setId(id);
        invoice.setEmail(email);
        invoice.setAddressLine(addressLine);
        invoice.setBlock(block);
        invoice.setDistrict(district);
        invoice.setProvince(province);
        invoice.setPhoneNumber(phoneNumber);
        return invoice;
    }
}
