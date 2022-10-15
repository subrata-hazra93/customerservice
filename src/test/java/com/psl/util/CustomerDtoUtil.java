package com.psl.util;

import com.psl.dto.CustomerDto;

public class CustomerDtoUtil {

    public static CustomerDto getCustomerDto(Long id,String accountNumber,String mobileNumber, String panNumber,
                                             String customerName, String dateOfBirth, String accountType,
                                             String productType, String dateOfOpening){
            CustomerDto customerDto=new CustomerDto();
            customerDto.setAccountNumber(accountNumber);
            customerDto.setMobileNumber(mobileNumber);
            customerDto.setPanNumber(panNumber);
            customerDto.setCustomerName(customerName);
            customerDto.setDateOfBirth(dateOfBirth);
            customerDto.setAccountType(accountType);
            customerDto.setProductType(productType);
            customerDto.setDateOfOpening(dateOfOpening);
            return customerDto;
    }
}
