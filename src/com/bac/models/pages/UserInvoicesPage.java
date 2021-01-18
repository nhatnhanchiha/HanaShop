package com.bac.models.pages;

import com.bac.models.entities.Category;
import com.bac.models.entities.Invoice;

import java.util.List;

public class UserInvoicesPage extends Page {
    private static final long serialVersionUID = 8713226466233522748L;
    public static final int SIZE_OF_INVOICES = 2;

    private List<Invoice> invoices;
    private int page;
    private boolean hasNextPage;

    public UserInvoicesPage(List<Category> categories, List<Invoice> invoices, int page) {
        super(categories);
        this.invoices = invoices;
        this.page = page;
        if (invoices.size() > SIZE_OF_INVOICES) {
            hasNextPage = true;
            invoices.remove(SIZE_OF_INVOICES);
        } else {
            hasNextPage = false;
        }
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }
}
