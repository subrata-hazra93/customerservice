package com.psl.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.psl.dto.CustomerDetailsDto;
import com.psl.dto.CustomerDto;
import com.psl.entity.Customer;
import com.psl.service.CustomerService;
import com.psl.service.CustomerServiceImpl;
import com.psl.util.CustomerDetailsDtoUtil;
import com.psl.util.CustomerDtoUtil;
import com.psl.util.CustomerUtil;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;
import static com.psl.util.CustomerConstant.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@MicronautTest
class CustomerControllerTest {
    @Inject
    CustomerController customerController;

    @Inject
    CustomerService customerService;


    @MockBean(CustomerServiceImpl.class)
    CustomerService customerService(){
        return mock(CustomerService.class);
    }

    @Test
    void testCreateCustomer_success() throws JsonProcessingException {
        CustomerDto customerDto= CustomerDtoUtil.getCustomerDto(CUSTOMER_ID,ACCOUNT_NUMBER, MOBILE_NUMBER, PAN_NUMBER,
                CUSTOMER_NAME, DATE_OF_BIRTH, ACCOUNT_TYPE, PRODUCT_TYPE, DATE_OF_OPENING);

        Customer customer= CustomerUtil.getCustomer(CUSTOMER_ID,ACCOUNT_NUMBER, MOBILE_NUMBER, PAN_NUMBER,
                CUSTOMER_NAME, DATE_OF_BIRTH, ACCOUNT_TYPE, PRODUCT_TYPE, DATE_OF_OPENING);

        CustomerDetailsDto customerDetailsDto= CustomerDetailsDtoUtil.getCustomerDetailsDto(CUSTOMER_ID,ACCOUNT_NUMBER, MOBILE_NUMBER, PAN_NUMBER,
                CUSTOMER_NAME, DATE_OF_BIRTH, ACCOUNT_TYPE, PRODUCT_TYPE, DATE_OF_OPENING);

        when(customerService.createCustomer(Mockito.any(Customer.class))).thenReturn(customer);
        HttpResponse<CustomerDetailsDto> response= customerController.createCustomer(customerDto);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatus());
        Assertions.assertNotNull(response.body());
        Assertions.assertEquals(customerDetailsDto,response.body());
    }
    @Test
    void testDeleteCustomerDetails_success(){
        HttpResponse<String> response=customerController.deleteCustomerDetails(1L);
        Assertions.assertNotNull(response.body());
        Assertions.assertEquals(HttpStatus.OK, response.getStatus());
    }

    @Test
    void testGetListOfCustomerDetails_success(){
        CustomerDto customerDto=CustomerDtoUtil.getCustomerDto(CUSTOMER_ID,ACCOUNT_NUMBER, MOBILE_NUMBER, PAN_NUMBER,
                CUSTOMER_NAME, DATE_OF_BIRTH, ACCOUNT_TYPE, PRODUCT_TYPE, DATE_OF_OPENING);

        List<Customer> listOfCustomer=new ArrayList<>();
        Customer customer= CustomerUtil.getCustomer(CUSTOMER_ID,ACCOUNT_NUMBER, MOBILE_NUMBER, PAN_NUMBER,
                CUSTOMER_NAME, DATE_OF_BIRTH, ACCOUNT_TYPE, PRODUCT_TYPE, DATE_OF_OPENING);
        listOfCustomer.add(customer);

        List<CustomerDetailsDto> customerDetailsDtoList=new ArrayList<>();
        CustomerDetailsDto customerDetailsDto= CustomerDetailsDtoUtil.getCustomerDetailsDto(CUSTOMER_ID,ACCOUNT_NUMBER, MOBILE_NUMBER, PAN_NUMBER,
                CUSTOMER_NAME, DATE_OF_BIRTH, ACCOUNT_TYPE, PRODUCT_TYPE, DATE_OF_OPENING);
        customerDetailsDtoList.add(customerDetailsDto);

        when(customerService.getListOfCustomer(Mockito.any(Customer.class))).thenReturn(listOfCustomer);
        HttpResponse<List<CustomerDetailsDto>> response=customerController.getListOfCustomerDetails(customerDto);
        Assertions.assertEquals(HttpStatus.OK, response.getStatus());
        Assertions.assertNotNull(response.body());
        Assertions.assertEquals(customerDetailsDtoList, response.body());
    }

    @Test
    void testUpdateCustomerDetails_success(){
        CustomerDto customerDto=CustomerDtoUtil.getCustomerDto(CUSTOMER_ID,ACCOUNT_NUMBER, MOBILE_NUMBER, PAN_NUMBER,
                CUSTOMER_NAME, DATE_OF_BIRTH, ACCOUNT_TYPE, PRODUCT_TYPE, DATE_OF_OPENING);

        Customer customer= CustomerUtil.getCustomer(null,ACCOUNT_NUMBER, MOBILE_NUMBER, PAN_NUMBER,
                CUSTOMER_NAME, DATE_OF_BIRTH, ACCOUNT_TYPE, PRODUCT_TYPE, DATE_OF_OPENING);

        CustomerDetailsDto customerDetailsDto= CustomerDetailsDtoUtil.getCustomerDetailsDto(null,ACCOUNT_NUMBER, MOBILE_NUMBER, PAN_NUMBER,
                CUSTOMER_NAME, DATE_OF_BIRTH, ACCOUNT_TYPE, PRODUCT_TYPE, DATE_OF_OPENING);

        when(customerService.updateCustomer(1L, customer)).thenReturn(customer);
        HttpResponse<CustomerDetailsDto> response= customerController.updateCustomerDetails(1L, customerDto);
        Assertions.assertEquals(HttpStatus.OK, response.getStatus());
        Assertions.assertNotNull(response.body());
        Assertions.assertEquals(customerDetailsDto, response.body());
    }

    @Test
    void testRetrieveCustomerDetails_success(){
        CustomerDetailsDto customerDetailsDto= CustomerDetailsDtoUtil.getCustomerDetailsDto(CUSTOMER_ID,ACCOUNT_NUMBER, MOBILE_NUMBER, PAN_NUMBER,
                CUSTOMER_NAME, DATE_OF_BIRTH, ACCOUNT_TYPE, PRODUCT_TYPE, DATE_OF_OPENING);

        Customer customer= CustomerUtil.getCustomer(CUSTOMER_ID,ACCOUNT_NUMBER, MOBILE_NUMBER, PAN_NUMBER,
                CUSTOMER_NAME, DATE_OF_BIRTH, ACCOUNT_TYPE, PRODUCT_TYPE, DATE_OF_OPENING);

        when(customerService.retrieveCustomer(1L)).thenReturn(customer);
        HttpResponse<CustomerDetailsDto> response= customerController.retrieveCustomerDetails(1L);
        Assertions.assertEquals(HttpStatus.OK, response.getStatus());
        Assertions.assertNotNull(response.body());
        Assertions.assertEquals(customerDetailsDto, response.body());

    }
    }




