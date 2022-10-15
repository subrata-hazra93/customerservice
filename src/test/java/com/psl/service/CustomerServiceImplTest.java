package com.psl.service;

import com.psl.constant.ConstantMessage;
import com.psl.entity.Customer;
import com.psl.exception.CustomException;
import com.psl.mapper.MapCustomerDetails;
import com.psl.repository.CustomerRepository;
import com.psl.util.*;
import io.micronaut.http.HttpStatus;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.psl.util.CustomerConstant.*;
import static com.psl.util.CustomerConstant.DATE_OF_OPENING;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@MicronautTest
class CustomerServiceImplTest {
    @Inject
    CustomerService customerService;
    @Inject
    CustomerRepository customerRepository;

    @MockBean(MapCustomerDetails.class)
    MapCustomerDetails mapCustomerDetails() {
        return mock(MapCustomerDetails.class);
    }


    @MockBean(CustomerRepository.class)
    CustomerRepository customerRepository() {
        return mock(CustomerRepository.class);
    }

    @Test
    void testCreateCustomer_success() {
        Customer customer = CustomerUtil.getCustomer(CUSTOMER_ID, ACCOUNT_NUMBER, MOBILE_NUMBER, PAN_NUMBER,
                CUSTOMER_NAME, DATE_OF_BIRTH, ACCOUNT_TYPE, PRODUCT_TYPE, DATE_OF_OPENING);
        when(customerRepository.save(customer)).thenReturn(customer);
        Customer response = customerService.createCustomer(customer);
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getAccountNumber());
        Assertions.assertNotNull(response.getPanNumber());
        Assertions.assertNotNull(response.getCustomerName());
        Assertions.assertNotNull(response.getDateOfBirth());
        Assertions.assertNotNull(response.getMobileNumber());
        Assertions.assertNotNull(response.getAccountType());
        Assertions.assertNotNull(response.getProductType());
        Assertions.assertNotNull(response.getDateOfOpening());
        Assertions.assertEquals(customer, response);
    }



    @Test()
    void testCreateCustomer_failure_when_requestBodyFieldsAreNull() {

        CustomException thrown = Assertions.assertThrows(CustomException.class, () -> {
            Customer customer = CustomerUtil.getCustomer(null, null, null, null, null,
                    null, null, null, null);
            customerService.createCustomer(customer);
            customerRepository.save(customer);
        });
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, thrown.getHttpStatus());
    }

    @Test()
    void testCreateCustomer_failure_when_requestBodyFieldsAreInvalid() {
        CustomException thrown = Assertions.assertThrows(CustomException.class, () -> {
            Customer customer = CustomerUtil.getCustomer(CUSTOMER_ID, INVALID_ACCOUNT_NUMBER, INVALID_MOBILE_NUMBER, INVALID_PAN_NUMBER,
                    INVALID_CUSTOMER_NAME, INVALID_DATE_OF_BIRTH, INVALID_ACCOUNT_TYPE, INVALID_PRODUCT_TYPE, INVALID_DATE_OF_OPENING);
            customerService.createCustomer(customer);
        });
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, thrown.getHttpStatus());
    }

    @Test
    void testCreateCustomer_failure_when_requestBodyFieldsAreEmpty() {
        CustomException thrown = Assertions.assertThrows(CustomException.class, () -> {
            Long id;
            Customer customer = CustomerUtil.getCustomer(null, "", "", "", "", "", "", "", "");
            customerService.createCustomer(customer);
        });
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, thrown.getHttpStatus());
    }

    @Test
    void testGetListOfCustomer_success_searchWithCustomerNameAndDob() {
        Customer request = CustomerUtil.getCustomer(null, null, null, null, CUSTOMER_NAME,
                DATE_OF_BIRTH, null, null, null);
        Customer customer = CustomerUtil.getCustomer(CUSTOMER_ID, ACCOUNT_NUMBER, MOBILE_NUMBER, PAN_NUMBER,
                CUSTOMER_NAME, DATE_OF_BIRTH, ACCOUNT_TYPE, PRODUCT_TYPE, DATE_OF_OPENING);
        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer);
        when(customerRepository.findByCustomerNameAndDateOfBirth(request.getCustomerName(), request.getDateOfBirth())).thenReturn(customerList);
        List<Customer> response = customerService.getListOfCustomer(request);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(customerList, response);
        assertTrue(
                response.stream()
                        .anyMatch(responses -> CUSTOMER_NAME.equals(responses.getCustomerName()))
        );
    }

    @Test
    void testGetLIstOfCustomer_success_searchWithAccountNumber(){
        Customer request = CustomerUtil.getCustomer(null, ACCOUNT_NUMBER, null, null, null,
                null, null, null, null);
        Customer customer = CustomerUtil.getCustomer(CUSTOMER_ID, ACCOUNT_NUMBER, MOBILE_NUMBER, PAN_NUMBER,
                CUSTOMER_NAME, DATE_OF_BIRTH, ACCOUNT_TYPE, PRODUCT_TYPE, DATE_OF_OPENING);
        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer);
        when(customerRepository.findByAccountNumber(request.getAccountNumber())).thenReturn(customerList);
        List<Customer> accountNumberResponse = customerService.getListOfCustomer(request);
        Assertions.assertNotNull(accountNumberResponse);
        Assertions.assertEquals(customerList, accountNumberResponse);
        assertTrue(
                accountNumberResponse.stream()
                        .anyMatch(accountNumberResponses -> ACCOUNT_NUMBER.equals(accountNumberResponses.getAccountNumber()))
        );
    }

    @Test
    void testGetLIstOfCustomer_success_searchWithPanNumber() {
        Customer request = CustomerUtil.getCustomer(null, null, null, PAN_NUMBER, null,
                null, null, null, null);
        Customer customer = CustomerUtil.getCustomer(CUSTOMER_ID, ACCOUNT_NUMBER, MOBILE_NUMBER, PAN_NUMBER,
                CUSTOMER_NAME, DATE_OF_BIRTH, ACCOUNT_TYPE, PRODUCT_TYPE, DATE_OF_OPENING);
        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer);
        when(customerRepository.findByPanNumber(request.getPanNumber())).thenReturn(customerList);
        List<Customer> panNumberResponse = customerService.getListOfCustomer(request);
        Assertions.assertNotNull(panNumberResponse);
        Assertions.assertEquals(customerList, panNumberResponse);
        assertTrue(
                panNumberResponse.stream()
                        .anyMatch(panNumberResponses -> PAN_NUMBER.equals(panNumberResponses.getPanNumber()))
        );
    }

    @Test
    void testGetListOfCustomer_success_searchWithMobileNumber() {
        Customer request = CustomerUtil.getCustomer(null, null, MOBILE_NUMBER, null, null,
                null, null, null, null);
        Customer customer = CustomerUtil.getCustomer(CUSTOMER_ID, ACCOUNT_NUMBER, MOBILE_NUMBER, PAN_NUMBER,
                CUSTOMER_NAME, DATE_OF_BIRTH, ACCOUNT_TYPE, PRODUCT_TYPE, DATE_OF_OPENING);
        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer);
        when(customerRepository.findByMobileNumber(request.getMobileNumber())).thenReturn(customerList);
        List<Customer> mobileNumberResponse = customerService.getListOfCustomer(request);
        Assertions.assertNotNull(mobileNumberResponse);
        Assertions.assertEquals(customerList, mobileNumberResponse);
        assertTrue(
                mobileNumberResponse.stream()
                        .anyMatch(mobileNumberResponses -> MOBILE_NUMBER.equals(mobileNumberResponses.getMobileNumber()))
        );
    }

    @Test
    void testGetListOfCustomer_success_searchWithAccountType() {
        Customer request = CustomerUtil.getCustomer(null, null, null, null, null,
                null, ACCOUNT_TYPE, null, null);
        Customer customer = CustomerUtil.getCustomer(CUSTOMER_ID, ACCOUNT_NUMBER, MOBILE_NUMBER, PAN_NUMBER,
                CUSTOMER_NAME, DATE_OF_BIRTH, ACCOUNT_TYPE, PRODUCT_TYPE, DATE_OF_OPENING);
        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer);
        when(customerRepository.findByAccountType(request.getAccountType())).thenReturn(customerList);
        List<Customer> accountTypeResponse = customerService.getListOfCustomer(request);
        Assertions.assertNotNull(accountTypeResponse);
        Assertions.assertEquals(customerList, accountTypeResponse);
        assertTrue(
                accountTypeResponse.stream()
                        .anyMatch(accountTypeResponses -> ACCOUNT_TYPE.equals(accountTypeResponses.getAccountType()))
        );
    }

    @Test
    void testGetListOfCustomer_success_searchWithProductType() {
        Customer request = CustomerUtil.getCustomer(null, null, null, null, null,
                null, null, PRODUCT_TYPE, null);
        Customer customer = CustomerUtil.getCustomer(CUSTOMER_ID, ACCOUNT_NUMBER, MOBILE_NUMBER, PAN_NUMBER,
                CUSTOMER_NAME, DATE_OF_BIRTH, ACCOUNT_TYPE, PRODUCT_TYPE, DATE_OF_OPENING);
        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer);
        when(customerRepository.findByProductType(request.getProductType())).thenReturn(customerList);
        List<Customer> productTypeResponse = customerService.getListOfCustomer(request);
        Assertions.assertNotNull(productTypeResponse);
        Assertions.assertEquals(customerList, productTypeResponse);
        assertTrue(
                productTypeResponse.stream()
                        .anyMatch(productTypeResponses -> PRODUCT_TYPE.equals(productTypeResponses.getProductType()))
        );
    }

    @Test
    void testGetListOfCustomer_success_searchWithDateOfOpening() {
        Customer request = CustomerUtil.getCustomer(null, null, null, null, null,
                null, null, null, DATE_OF_OPENING);
        Customer customer = CustomerUtil.getCustomer(CUSTOMER_ID, ACCOUNT_NUMBER, MOBILE_NUMBER, PAN_NUMBER,
                CUSTOMER_NAME, DATE_OF_BIRTH, ACCOUNT_TYPE, PRODUCT_TYPE, DATE_OF_OPENING);
        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer);
        when(customerRepository.findByDateOfOpening(request.getDateOfOpening())).thenReturn(customerList);
        List<Customer> dateOfOpeningResponse = customerService.getListOfCustomer(request);
        Assertions.assertNotNull(dateOfOpeningResponse);
        Assertions.assertEquals(customerList, dateOfOpeningResponse);
        assertTrue(
                dateOfOpeningResponse.stream()
                        .anyMatch(dateOfOpeningResponses -> DATE_OF_OPENING.equals(dateOfOpeningResponses.getDateOfOpening()))
        );
    }

    @Test
    void testGetListOfCustomer_failure_validateCustomer() {
        Customer customer = CustomerUtil.getCustomer(null, null, null, null, null,
                null, null, null, null);
        CustomException thrown = Assertions.assertThrows(CustomException.class, () -> {
            CustomerValidation.isAllCustomerDetailsPresent(customer);
        });
        Assertions.assertEquals(thrown.getHttpStatus(),HttpStatus.BAD_REQUEST);
    }

    @Test
    void testGetListOfCustomer_failure_validateCustomerFields() {
        Customer customer = CustomerUtil.getCustomer(null, null, INVALID_MOBILE_NUMBER, INVALID_PAN_NUMBER, null,
                null, null, null, null);
        CustomException thrown = Assertions.assertThrows(CustomException.class, () -> {
            CustomerFieldsValidator.validateCustomerRequestFormat(customer);
        });
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, thrown.getHttpStatus());

    }

    @Test
    void testGetListOfCustomer_failure_validateDobAndCustomerName() {
        Customer customer = CustomerUtil.getCustomer(null, null, null, null, INVALID_CUSTOMER_NAME,
                INVALID_DATE_OF_BIRTH, null, null, null);
        CustomException thrown = Assertions.assertThrows(CustomException.class, () -> {
            CustomerFieldsValidator.validateCustomerRequestFormat(customer);
        });
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, thrown.getHttpStatus());
    }

    @Test
    void testUpdateCustomer_success() {
        Customer customer = CustomerUtil.getCustomer(CUSTOMER_ID, ACCOUNT_NUMBER, MOBILE_NUMBER, PAN_NUMBER,
                CUSTOMER_NAME, DATE_OF_BIRTH, ACCOUNT_TYPE, PRODUCT_TYPE, DATE_OF_OPENING);
        when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.ofNullable(customer));
        when(customerRepository.update(customer)).thenReturn(customer);
        Customer response = customerService.updateCustomer(CUSTOMER_ID, customer);
        assertEquals(response, customer);
    }

    @Test
    void testUpdateCustomer_failure_customerFieldsAreNull(){
        Customer body = CustomerUtil.getCustomer(CUSTOMER_ID, null, null, null, null,
                null, null, null, null);
        Customer response=CustomerUtil.getCustomer(CUSTOMER_ID, ACCOUNT_NUMBER, MOBILE_NUMBER, PAN_NUMBER,
                CUSTOMER_NAME, DATE_OF_BIRTH, ACCOUNT_TYPE, PRODUCT_TYPE, DATE_OF_OPENING);
        when(customerRepository.findById(anyLong())).thenReturn(Optional.ofNullable(response));
        CustomException thrown = assertThrows(CustomException.class, ()->{
            CustomerValidation.isAllCustomerDetailsAreEmpty(body);
            customerService.updateCustomer(body.getId(), body);
            customerRepository.update(body);
        });
        Assertions.assertEquals("Please Enter AtLeast one attribute or attributes should not be empty", thrown.getMessage());
    }

    @Test
    void testUpdateCustomer_failure_dataNotFoundToUpdate() {
        Customer customer = CustomerUtil.getCustomer(null, ACCOUNT_NUMBER, MOBILE_NUMBER, PAN_NUMBER,
                CUSTOMER_NAME, DATE_OF_BIRTH, ACCOUNT_TYPE, PRODUCT_TYPE, DATE_OF_OPENING);
        CustomException thrown = assertThrows(CustomException.class, () -> {
            customerService.updateCustomer(customer.getId(), customer);
            customerRepository.findById(null);
        });
        Assertions.assertEquals("Customer details not found for update", thrown.getMessage());
    }

    @Test
    void testDeleteCustomerDetails_success_when_idIsPresent() {
        Customer customer = CustomerUtil.getCustomer(CUSTOMER_ID, ACCOUNT_NUMBER, MOBILE_NUMBER, PAN_NUMBER,
                CUSTOMER_NAME, DATE_OF_BIRTH, ACCOUNT_TYPE, PRODUCT_TYPE, DATE_OF_OPENING);

        when(customerRepository.findById(1L)).thenReturn(Optional.ofNullable(customer));
        doNothing().when(customerRepository).delete(any());
        customerService.deleteCustomerDetails(CUSTOMER_ID);
        verify(customerRepository, times(1)).delete(customer);
    }

    @Test
    void testDeleteCustomerDetails_failure_idIsNotPresent() {
        Long id = 2L;
        CustomException thrown = assertThrows(CustomException.class, () -> {
            customerService.deleteCustomerDetails(id);
            customerRepository.findById(id);
        });
        Assertions.assertEquals("No Data Found to Delete", thrown.getMessage());
    }

    @Test
    void testRetrieveCustomerDetails_success() {
        Long id = 1L;
        Customer customer = CustomerUtil.getCustomer(CUSTOMER_ID, ACCOUNT_NUMBER, MOBILE_NUMBER, PAN_NUMBER,
                CUSTOMER_NAME, DATE_OF_BIRTH, ACCOUNT_TYPE, PRODUCT_TYPE, DATE_OF_OPENING);
        when(customerRepository.findById(id)).thenReturn(Optional.ofNullable(customer));
        Customer response = customerService.retrieveCustomer(id);
        Assertions.assertEquals(customer, response);
    }

    @Test
    void testRetrieveCustomer_failure_idIsNotPresent() {
        Long id = 2L;
        CustomException thrown = assertThrows(CustomException.class, () -> {
            customerService.retrieveCustomer(id);
            customerRepository.findById(id);
        });
        Assertions.assertEquals("Customer details not found: Please Enter valid Id", thrown.getMessage());
    }
}





