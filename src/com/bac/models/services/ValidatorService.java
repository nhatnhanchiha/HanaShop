package com.bac.models.services;

import com.oreilly.servlet.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author nhatn
 */
public class ValidatorService {
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{1,6}$", Pattern.CASE_INSENSITIVE);
    public static final int MAX_LENGTH_OF_TEXT = 50;
    public static final int MIN_LENGTH_OF_TEXT = 1;
    public static final int MAX_LENGTH_OF_USERNAME = 100;
    public static final int MIN_LENGTH_OF_USERNAME = 5;
    public static final int MAX_LENGTH_OF_PASSWORD = 50;
    public static final int MIN_LENGTH_OF_PASSWORD = 5;
    public static final int MIN_LENGTH_OF_FIRST_NAME = 1;
    public static final int MAX_LENGTH_OF_FIRST_NAME = 50;
    public static final int MIN_LENGTH_OF_LAST_NAME = 1;
    public static final int MAX_LENGTH_OF_LAST_NAME = 50;
    public static final int MAX_LENGTH_OF_SHORT_DESCRIPTION = 75;
    public static final int MIN_LENGTH_OF_DESCRIPTION = 1;
    public static final int MAX_LENGTH_OF_LONG_DESCRIPTION = 500;
    public static final int MIN_LENGTH_OF_PRODUCT_NAME = 1;
    public static final int MAX_LENGTH_OF_PRODUCT_NAME = 50;
    public static final double MIN_VALUE_OF_PRICE = 0.1;
    public static final double MAX_VALUE_OF_PRICE = 2000;
    public static final int MIN_VALUE_OF_QUANTITY = 0;
    public static final int MAX_VALUE_OF_QUANTITY = 200000;
    public static final int MAX_LENGTH_OF_ADDRESS_INPUT = 100;
    public static final String VALID_PHONE_NUMBER_REGEX = "^((0[3|5|7|8|9])+([0-9]{8}))|^\\+?((84|84[3|5|7|8|9])+([0-9]{9}))\\b$";


    public static Boolean validateRegisterRequest(HttpServletRequest request) {
        String username = request.getParameter("Input.Username");
        if (username == null || username.trim().length() < MIN_LENGTH_OF_USERNAME
                || username.length() > MAX_LENGTH_OF_USERNAME) {
            return false;
        }

        String password = request.getParameter("Input.Password");
        if (password == null || password.trim().length() < MIN_LENGTH_OF_PASSWORD || password.length() > MAX_LENGTH_OF_PASSWORD) {
            return false;
        }

        String confirmPassword = request.getParameter("Input.ConfirmPassword");
        if (!password.equals(confirmPassword)) {
            return false;
        }

        String firstName = request.getParameter("Input.FirstName");
        if (firstName == null || firstName.trim().length() < MIN_LENGTH_OF_FIRST_NAME || firstName.length() > MAX_LENGTH_OF_FIRST_NAME) {
            return false;
        }

        String lastName = request.getParameter("Input.LastName");
        if (lastName == null || lastName.trim().length() < MIN_LENGTH_OF_LAST_NAME || lastName.length() > MAX_LENGTH_OF_LAST_NAME) {
            return false;
        }

        return true;
    }

    public static Boolean validateLoginRequest(HttpServletRequest request) {
        String username = request.getParameter("Input.Username");
        if (username == null || username.trim().length() < MIN_LENGTH_OF_USERNAME || username.length() > MAX_LENGTH_OF_USERNAME) {
            return false;
        }

        String password = request.getParameter("Input.Password");
        if (password == null || password.trim().length() < MIN_LENGTH_OF_PASSWORD || password.length() > MAX_LENGTH_OF_PASSWORD) {
            return false;
        }

        return true;
    }

    public static Boolean validateAddProductRequest(MultipartRequest request) {
        String name = convert(request.getParameter("Input.Name"));
        if (name == null || name.trim().length() < MIN_LENGTH_OF_PRODUCT_NAME || name.length() > MAX_LENGTH_OF_PRODUCT_NAME) {
            return false;
        }

        String shortDescription = convert(request.getParameter("Input.ShortDescription"));
        if (shortDescription == null || shortDescription.trim().length() < MIN_LENGTH_OF_DESCRIPTION || shortDescription.length() > MAX_LENGTH_OF_SHORT_DESCRIPTION) {
            return false;
        }


        String longDescription = convert(request.getParameter("Input.LongDescription"));
        if (longDescription == null || longDescription.trim().length() < MIN_LENGTH_OF_DESCRIPTION || longDescription.length() > MAX_LENGTH_OF_LONG_DESCRIPTION) {
            return false;
        }

        String categoryIdStr = request.getParameter("Selected.CategoryId");
        int categoryId;
        try {
            categoryId = Integer.parseInt(categoryIdStr);
        } catch (NumberFormatException e) {
            return false;
        }

        if (categoryId <= 0) {
            return false;
        }

        String priceStr = request.getParameter("Input.Price");
        double price;
        try {
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            return false;
        }

        if (price < MIN_VALUE_OF_PRICE || price > MAX_VALUE_OF_PRICE) {
            return false;
        }

        String quantityStr = request.getParameter("Input.Quantity");
        int quantity;
        try {
            quantity = Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            return false;
        }

        if (quantity < MIN_VALUE_OF_QUANTITY || quantity > MAX_VALUE_OF_QUANTITY) {
            return false;
        }

        return true;
    }

    public static Boolean validateEditProductRequest(MultipartRequest request) {
        String productIdStr = request.getParameter("productId");
        int productId;
        try {
            productId = Integer.parseInt(productIdStr);
        } catch (NumberFormatException e) {
            return false;
        }

        if (productId <= 0) {
            return false;
        }

        String name = convert(request.getParameter("Input.Name"));
        if (name == null || name.trim().length() < MIN_LENGTH_OF_PRODUCT_NAME || name.length() > MAX_LENGTH_OF_PRODUCT_NAME) {
            return false;
        }


        String shortDescription = convert(request.getParameter("Input.ShortDescription"));
        System.out.println("shortDescription = " + shortDescription);
        if (shortDescription == null || shortDescription.trim().length() < MIN_LENGTH_OF_DESCRIPTION || shortDescription.length() > MAX_LENGTH_OF_SHORT_DESCRIPTION) {
            return false;
        }


        String longDescription = convert(request.getParameter("Input.LongDescription"));
        if (longDescription == null || longDescription.trim().length() < MIN_LENGTH_OF_DESCRIPTION || longDescription.length() > MAX_LENGTH_OF_LONG_DESCRIPTION) {
            return false;
        }



        String categoryIdStr = request.getParameter("Selected.CategoryId");
        int categoryId;
        try {
            categoryId = Integer.parseInt(categoryIdStr);
        } catch (NumberFormatException e) {
            return false;
        }

        if (categoryId <= 0) {
            return false;
        }

        String priceStr = request.getParameter("Input.Price");
        double price;
        try {
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            return false;
        }

        if (price < MIN_VALUE_OF_PRICE || price > MAX_VALUE_OF_PRICE) {
            return false;
        }

        String quantityStr = request.getParameter("Input.Quantity");
        int quantity;
        try {
            quantity = Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            return false;
        }

        if (quantity < MIN_VALUE_OF_QUANTITY || quantity > MAX_VALUE_OF_QUANTITY) {
            return false;
        }


        return true;
    }

    public static Boolean validateCheckingOutRequest(HttpServletRequest request) {
        String addressLine = request.getParameter("Input.AddressLine");
        if (addressLine == null
                || addressLine.trim().length() < MIN_LENGTH_OF_TEXT
                || addressLine.length() > MAX_LENGTH_OF_ADDRESS_INPUT) {
            return false;
        }

        String block = request.getParameter("Input.Block");
        if (block == null
                || block.trim().length() < MIN_LENGTH_OF_TEXT
                || block.length() > MAX_LENGTH_OF_ADDRESS_INPUT) {
            return false;
        }

        String district = request.getParameter("Input.District");
        if (district == null
                || district.trim().length() < MIN_LENGTH_OF_TEXT
                || district.length() > MAX_LENGTH_OF_ADDRESS_INPUT) {
            return false;
        }

        String province = request.getParameter("Input.Province");
        if (province == null
                || province.trim().length() < MIN_LENGTH_OF_TEXT
                || province.length() > MAX_LENGTH_OF_ADDRESS_INPUT) {
            return false;
        }

        String phoneNumber = request.getParameter("Input.PhoneNumber");
        if (phoneNumber == null
                || !phoneNumber.matches(VALID_PHONE_NUMBER_REGEX)) {
            return false;
        }

        return true;
    }

    private static String convert(String s) {
        if (s == null) {
            return null;
        }
        byte[] ptext = s.getBytes(ISO_8859_1);
        return new String(ptext, UTF_8);
    }
}
