package com.bac.models.utilities;

import com.bac.models.daos.AccountDao;
import com.bac.models.daos.AdminDao;
import com.bac.models.daos.CategoryDao;
import com.bac.models.daos.GoogleUserDao;
import com.bac.models.daos.InvoiceDao;
import com.bac.models.daos.InvoiceDetailDao;
import com.bac.models.daos.LogSqlDao;
import com.bac.models.daos.PaypalDao;
import com.bac.models.daos.ProductDao;
import com.bac.models.daos.impls.AccountDaoImpl;
import com.bac.models.daos.impls.AdminDaoImpl;
import com.bac.models.daos.impls.CategoryDaoImpl;
import com.bac.models.daos.impls.GoogleUserDaoImpl;
import com.bac.models.daos.impls.InvoiceDaoImpl;
import com.bac.models.daos.impls.InvoiceDetailDaoImpl;
import com.bac.models.daos.impls.LogSqlDaoImpl;
import com.bac.models.daos.impls.PaypalDaoImpl;
import com.bac.models.daos.impls.ProductDaoImpl;

import javax.naming.NamingException;
import java.sql.SQLException;

/**
 * @author nhatn
 */
public class HanaShopContext extends DbContext {
    private ProductDao productDao;
    private CategoryDao categoryDao;
    private AccountDao accountDao;
    private LogSqlDao logSqlDao;
    private AdminDao adminDao;
    private InvoiceDao invoiceDao;
    private InvoiceDetailDao invoiceDetailDao;
    private GoogleUserDao googleUserDao;
    private PaypalDao paypalDao;


    public HanaShopContext() throws SQLException, NamingException {
        super("DB007");
    }

    public ProductDao getProductDao() {
        if (productDao == null) {
            productDao = new ProductDaoImpl(connection);
        }

        return productDao;
    }

    public CategoryDao getCategoryDao() {
        if (categoryDao == null) {
            categoryDao = new CategoryDaoImpl(connection);
        }

        return categoryDao;
    }

    public AccountDao getAccountDao() {
        if (accountDao == null) {
            accountDao = new AccountDaoImpl(connection);
        }

        return accountDao;
    }

    public LogSqlDao getLogSqlDao() {
        if (logSqlDao == null) {
            logSqlDao = new LogSqlDaoImpl(connection);
        }

        return logSqlDao;
    }

    public AdminDao getAdminDao() {
        if (adminDao == null) {
            adminDao = new AdminDaoImpl(connection);
        }

        return adminDao;
    }

    public InvoiceDao getInvoiceDao() {
        if (invoiceDao == null) {
            invoiceDao = new InvoiceDaoImpl(connection);
        }

        return invoiceDao;
    }

    public InvoiceDetailDao getInvoiceDetailDao() {
        if (invoiceDetailDao == null) {
            invoiceDetailDao = new InvoiceDetailDaoImpl(connection);
        }

        return invoiceDetailDao;
    }

    public GoogleUserDao getGoogleUserDao() {
        if (googleUserDao == null) {
            googleUserDao = new GoogleUserDaoImpl(connection);
        }
        return googleUserDao;
    }

    public PaypalDao getPaypalDao() {
        if (paypalDao == null) {
            paypalDao = new PaypalDaoImpl(connection);
        }

        return paypalDao;
    }

    public String getInfo() {
        return "Hana Shop Context";
    }
}