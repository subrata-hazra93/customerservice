package com.psl.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.psl.entity.Customer;
import com.psl.exception.CustomException;
import java.util.List;

public interface CustomerService {
    Customer createCustomer(Customer customer) throws CustomException, JsonProcessingException;
    void deleteCustomerDetails(Long id);
    List<Customer> getListOfCustomer(Customer fields) throws CustomException;
    Customer updateCustomer(Long id, Customer body);
    Customer retrieveCustomer(Long id);
}
