package com.psl.util;

import com.psl.dto.CustomerDetailsDto;

import static com.psl.util.CustomerConstant.*;

public class CustomerDetailsDtoUtil {


    public static CustomerDetailsDto getCustomerDetailsDto(Long id, String accountNumber, String mobileNumber,
                                                           String panNumber, String customerName, String dateOfBirth,
                                                           String accountType, String productType, String dateOfOpening) {
        CustomerDetailsDto customerDetailsDto = new CustomerDetailsDto();
        customerDetailsDto.setId(id);
        customerDetailsDto.setAccountNumber(accountNumber);
        customerDetailsDto.setMobileNumber(mobileNumber);
        customerDetailsDto.setPanNumber(panNumber);
        customerDetailsDto.setCustomerName(customerName);
        customerDetailsDto.setDateOfBirth(dateOfBirth);
        customerDetailsDto.setAccountType(accountType);
        customerDetailsDto.setProductType(productType);
        customerDetailsDto.setDateOfOpening(dateOfOpening);
        return customerDetailsDto;
    }
}
