package com.psl.util;


import com.psl.entity.Customer;

public class CustomerUtil {


        public static Customer getCustomer(Long id,String accountNumber, String mobileNumber, String panNumber,
                                           String customerName, String dateOfBirth, String accountType,
                                           String productType, String dateOfOpening){
            Customer customer=new Customer();
            customer.setId(id);
            customer.setAccountNumber(accountNumber);
            customer.setMobileNumber(mobileNumber);
            customer.setPanNumber(panNumber);
            customer.setCustomerName(customerName);
            customer.setDateOfBirth(dateOfBirth);
            customer.setAccountType(accountType);
            customer.setProductType(productType);
            customer.setDateOfOpening(dateOfOpening);
            return customer;
        }

}
