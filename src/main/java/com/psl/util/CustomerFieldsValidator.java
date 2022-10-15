package com.psl.util;

import com.psl.constant.ConstantMessage;
import com.psl.entity.Customer;
import com.psl.exception.CustomException;
import io.micronaut.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerFieldsValidator {

    private static final Logger logger = LoggerFactory.getLogger(CustomerFieldsValidator.class);

    private CustomerFieldsValidator(){}

    /**
     * Check if all request parameters in request are in valid format
     * @param customer
     * @return ErrorMessageResponse
     */
    public static void validateCustomerRequestFormat(Customer customer) {

        List<String> errorList=new ArrayList<>();

        if (invalidAccountNumberParam(customer.getAccountNumber())){
            logger.info("CustomerFieldValidator.validateCustomerRequestParam  :: invalid account number format  = {}",
                    customer.getAccountNumber());
            errorList.add(ConstantMessage.VALID_ACCOUNT_NUMBER);
        }

        if (invalidMobileParam(customer.getMobileNumber())) {
            logger.info("CustomerFieldValidator.validateCustomerRequestParam  :: invalid mobile number format  = {}",
                    customer.getMobileNumber());
            errorList.add(ConstantMessage.VALID_MOBILE_NUMBER);
        }

        if (invalidPanParam(customer.getPanNumber())) {
            logger.info("CustomerFieldValidator.validateCustomerRequestParam :: invalid pan format  = {}",
                    customer.getPanNumber());
            errorList.add(ConstantMessage.VALID_PAN_NUMBER);
        }


        if (invalidCustomerNameParam(customer.getCustomerName()) ) {
            logger.info("CustomerFieldValidator.validateCustomerRequestParam  :: invalid customer name minimum = {}",
                    customer.getCustomerName() );
            errorList.add(ConstantMessage.VALID_CUSTOMER_NAME);
        }
        if (invalidDobParam(customer.getDateOfBirth())) {
            logger.info("CustomerFieldValidator.invalidDobParam  :: invalid date of birth format = {}",
                    customer.getCustomerName() + "," + customer.getDateOfBirth());
            errorList.add(ConstantMessage.VALID_DOB);
        }
        if (invalidateAccountType(customer.getAccountType())) {
            logger.info("CustomerFieldValidator.validateCustomerRequestParam  :: invalid account type format = {}",
                    customer.getAccountType());
            errorList.add(ConstantMessage.VALID_ACCOUNT_TYPE);
        }

        if (invalidateProductType(customer.getProductType())) {
            logger.info("CustomerFieldValidator.validateCustomerRequestParam  :: invalid product type format = {}",
                    customer.getProductType());
            errorList.add(ConstantMessage.VALID_PRODUCT_TYPE);
        }

        if (invalidateDateOfOpening(customer.getDateOfOpening())) {
            logger.info("CustomerFieldValidator.validateCustomerRequestParam  :: invalid date of opening format = {}",
                    customer.getDateOfOpening());
            errorList.add(ConstantMessage.VALID_DATE_OPENING);
        }
        if(!errorList.isEmpty()){
            throw new CustomException(errorList.toString(), HttpStatus.BAD_REQUEST);
        }


    }



    /**
     * Check if pan value is not null and then validate it
     * @param pan
     * @return boolean
     */
    public static boolean invalidPanParam( String pan) {
        return !StringUtil.isEmpty(pan) && !validatePan(pan);
    }

    /**
     * Check pan according regex pattern
     * @param pan
     * @return boolean
     */
    public static boolean validatePan( String pan) {
        String regex = "^[A-Z]{5}[0-9]{4}[A-Z]{1}$";
        return validatePatternMatch(regex, pan);
    }

    /**
     * Check if mobile value is not null and then validate it
     * @param mobile
     * @return boolean
     */
    public static boolean invalidMobileParam( String mobile) {
        return !StringUtil.isEmpty(mobile) && !validateMobile(mobile);
    }

    /**
     * Check mobile according regex pattern
     * @param mobile
     * @return boolean
     */
    public static boolean validateMobile(String mobile) {
        String regex = "(0)?[7-9][0-9]{9}";
        return validatePatternMatch(regex, mobile);
    }

    /**
     * Check if accountNumber value is not null and then validate it
     * @param accountNumber
     * @return boolean
     */
    public static boolean invalidAccountNumberParam(String accountNumber) {
        return !StringUtil.isEmpty(accountNumber) && !validateAccountNumber(accountNumber);
    }

    /**
     * Check accountNumber according regex pattern
     * @param accountNumber
     * @return boolean
     */
    public static boolean validateAccountNumber(String accountNumber) {
        String regex = "^[0-9]{10}$";
        return validatePatternMatch(regex, accountNumber);
    }

    /**
     * Check if customerName value is not null and then validate it
     * @param customerName
     * @return boolean
     */
    public static boolean invalidCustomerNameParam(String customerName) {
        return !StringUtil.isEmpty(customerName) && !validateCustomerName(customerName);
    }

    /**
     * Check dob according regex pattern
     * @param customerName
     * @return boolean
     */
    public static boolean validateCustomerName(String customerName) {
        String regex = "[A-Za-z ]{3,80}$";
        return validatePatternMatch(regex, customerName);
    }

    /**
     * Check if dob value is not null and then validate it
     * @param dob
     * @return boolean
     */
    public static boolean invalidDobParam(String dob) {
        return !StringUtil.isEmpty(dob) && !validateDob(dob);
    }

    /**
     * Check dob according regex pattern
     * @param dob
     * @return boolean
     */
    public static boolean validateDob(String dob) {
        String regex = "^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d$";
        return validatePatternMatch(regex, dob);
    }

    /**
     * Check if accountType value is not null and then validate it
     * @param accountType
     * @return boolean
     */
    public static boolean invalidateAccountType(String accountType) {
        return !StringUtil.isEmpty(accountType) && !validateAccountType(accountType);
    }

    /**
     * Check accountType according regex pattern
     * @param accountType
     * @return boolean
     */
    public static boolean validateAccountType(String accountType) {
        String regex = "[A-Za-z ]{4,30}";
        return validatePatternMatch(regex, accountType);
    }

    /**
     * Check if productType value is not null and then validate it
     * @param productType
     * @return boolean
     */
    public static boolean invalidateProductType( String productType) {
        return !StringUtil.isEmpty(productType) && !validateProductType(productType);
    }

    /**
     * Check productType according regex pattern
     * @param productType
     * @return boolean
     */
    public static boolean validateProductType( String productType) {
        String regex = "[A-Za-z ]{4,20}";
        return validatePatternMatch(regex, productType);
    }

    /**
     * Check if dateOfOpening value is not null and then validate it
     * @param dateOfOpening
     * @return boolean
     */
    public static boolean invalidateDateOfOpening( String dateOfOpening) {
        return !StringUtil.isEmpty(dateOfOpening) && !validateDateOfOpening(dateOfOpening);
    }

    /**
     * Check dateOfOpening according regex pattern
     * @param dateOfOpening
     * @return boolean
     */
    public static boolean validateDateOfOpening( String dateOfOpening) {
        final String regex = "^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d$";
        return validatePatternMatch(regex, dateOfOpening);
    }

    /**
     * Validate input value using regex
     * @param regex
     * @param customerInputValue
     * @return boolean
     */
    public static boolean validatePatternMatch( String regex,  String customerInputValue){
         Pattern pattern = Pattern.compile(regex);
         Matcher matcher = pattern.matcher(customerInputValue);
        return matcher.matches();
    }
}
