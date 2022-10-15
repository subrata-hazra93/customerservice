package com.psl.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.psl.constant.ConstantMessage;
import com.psl.entity.Customer;
import com.psl.exception.CustomException;
import io.micronaut.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.LinkedHashMap;
import java.util.Map;

public class CustomerValidation {
    private static final Logger logger = LoggerFactory.getLogger(CustomerValidation.class);

    private CustomerValidation(){}
    /**
     * Check if customer name and date of birth is valid or not
     * @param request
     * @return void
     */
    public static void isValidCustomerNameAndDob(Customer request) throws CustomException {

        if ((!(request.getCustomerName() == null || request.getCustomerName().isEmpty())
                && (request.getDateOfBirth() == null || request.getDateOfBirth().isEmpty())) ||
                ((request.getCustomerName() == null || request.getCustomerName().isEmpty())
                        && !(request.getDateOfBirth() == null || request.getDateOfBirth().isEmpty()))) {
            logger.info("CustomerValidation.isValidCustomerNameAndDob  :: invalid customer name and date of birth  = {}",
                    request);
            String msg = ConstantMessage.DOB_WITH_CUSTOMER_NAME;
            throw new CustomException(msg, HttpStatus.BAD_REQUEST);

        }
    }

    /**
     * Check if all request parameters in request are present or not
     * @param request
     * @return Void
     */
    public static void isAllCustomerDetailsPresent(Customer request) throws JsonProcessingException {
        Map<String, String> errorMap= new LinkedHashMap<>();
        if(StringUtil.isEmpty(request.getAccountNumber())){
            errorMap.put("Account Number",ConstantMessage.REQUIRED_ACCOUNT_NUMBER);
        }
        if(StringUtil.isEmpty(request.getMobileNumber())){
            errorMap.put("Mobile Number",ConstantMessage.REQUIRED_MOBILE_NUMBER);
        }
        if(StringUtil.isEmpty(request.getPanNumber())){
            errorMap.put("Pan Number",ConstantMessage.REQUIRED_PAN_NUMBER);
        }
        if(StringUtil.isEmpty(request.getCustomerName())){
            errorMap.put("Customer Name",ConstantMessage.REQUIRED_CUSTOMER_NAME);
        }
        if(StringUtil.isEmpty(request.getDateOfBirth())){
            errorMap.put("Date of Birth",ConstantMessage.REQUIRED_DATE_OF_BIRTH);
        }
        if(StringUtil.isEmpty(request.getAccountType())){
            errorMap.put("Account Type",ConstantMessage.REQUIRED_ACCOUNT_TYPE);
        }
        if(StringUtil.isEmpty(request.getProductType())){
            errorMap.put("Product Type",ConstantMessage.REQUIRED_PRODUCT_TYPE);
        }
        if(StringUtil.isEmpty(request.getDateOfOpening())){
            errorMap.put("Date of Opening",ConstantMessage.REQUIRED_DATE_OF_OPENING);
        }
        if(!errorMap.isEmpty()){
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(errorMap);
            throw new CustomException(json.replace('\"',' '), HttpStatus.BAD_REQUEST);
        }
    }

    public static void isAllCustomerDetailsAreEmpty(Customer request) {
        if( StringUtil.isEmpty(request.getAccountNumber()) && StringUtil.isEmpty(request.getPanNumber()) && StringUtil.isEmpty(request.getMobileNumber()) && StringUtil.isEmpty(request.getCustomerName()) && StringUtil.isEmpty(request.getDateOfBirth()) && StringUtil.isEmpty(request.getAccountType()) && StringUtil.isEmpty(request.getProductType()) && StringUtil.isEmpty(request.getDateOfOpening())){
            logger.info("CustomerValidation.isAllCustomerDetailsAreEmpty  :: all the customer are empty  = {}",
                    request);
            String message=ConstantMessage.ATTRIBUTES_NOT_EMPTY;
            throw new CustomException(message,HttpStatus.BAD_REQUEST);
        }
    }


}





