package com.psl.mapper;

import com.psl.dto.CustomerDetailsDto;
import com.psl.dto.CustomerDto;
import com.psl.entity.Customer;
import com.psl.util.CustomerFieldsValidator;
import com.psl.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapCustomerDetails {
    private static final Logger logger = LoggerFactory.getLogger(MapCustomerDetails.class);

    private MapCustomerDetails(){

    }
    /**
     * Map customer dto Object to customer entity
     * @return Customer
     */
    public static Customer mapCustomerDTOtoCustomerEntity(CustomerDto customerCreateDto) {
        logger.info("MapCustomerDetails.mapCustomerDTOtoCustomerEntity :: map customerDto to customer entity  = {}", customerCreateDto);
        Customer customerCreate = new Customer();
        customerCreate.setAccountNumber(customerCreateDto.getAccountNumber());
        customerCreate.setMobileNumber(customerCreateDto.getMobileNumber());
        customerCreate.setPanNumber(customerCreateDto.getPanNumber());
        customerCreate.setCustomerName(customerCreateDto.getCustomerName());
        customerCreate.setDateOfBirth(customerCreateDto.getDateOfBirth());
        customerCreate.setAccountType(customerCreateDto.getAccountType());
        customerCreate.setProductType(customerCreateDto.getProductType());
        customerCreate.setDateOfOpening(customerCreateDto.getDateOfOpening());
        return customerCreate;

    }

    /**
     * Map  customer entity to dto Object
     * @return CustomerDto
     */
    public static CustomerDetailsDto mapCustomerEntityToCustomerDto(Customer customerCreate) {
        logger.info("MapCustomerDetails.mapCustomerDTOtoCustomerEntity :: map customer entity to customerDto  = {}", customerCreate);
        CustomerDetailsDto customerCreateDto = new CustomerDetailsDto();
        customerCreateDto.setId(customerCreate.getId());
        customerCreateDto.setAccountNumber(customerCreate.getAccountNumber());
        customerCreateDto.setMobileNumber(customerCreate.getMobileNumber());
        customerCreateDto.setPanNumber(customerCreate.getPanNumber());
        customerCreateDto.setCustomerName(customerCreate.getCustomerName());
        customerCreateDto.setDateOfBirth(customerCreate.getDateOfBirth());
        customerCreateDto.setAccountType(customerCreate.getAccountType());
        customerCreateDto.setProductType(customerCreate.getProductType());
        customerCreateDto.setDateOfOpening(customerCreate.getDateOfOpening());
        return customerCreateDto;

    }

    /**
     * Update customer request body value with database customer value
     * @param customer
     * @return CustomerDto
     */
    public static Customer updateCustomer(Customer body, Customer customer) {
        CustomerFieldsValidator.validateCustomerRequestFormat(body);
        if (!StringUtil.isEmpty(body.getAccountNumber())) {
            logger.info(" MapCustomerDetails.updateCustomer:: check account number validation and set  = {}", body.getAccountNumber());
            customer.setAccountNumber(body.getAccountNumber());
        }

        if (!StringUtil.isEmpty(body.getMobileNumber())) {
            logger.info(" MapCustomerDetails.updateCustomer:: check mobile number validation and set  = {}", body.getMobileNumber());
                customer.setMobileNumber(body.getMobileNumber());
        }

        if (!StringUtil.isEmpty(body.getPanNumber()) ) {
            logger.info(" MapCustomerDetails.updateCustomer:: check account pan validation and set  = {}", body.getPanNumber());
            customer.setPanNumber(body.getPanNumber());
        }

        if (!StringUtil.isEmpty(body.getCustomerName())) {
            logger.info(" MapCustomerDetails.updateCustomer:: check customer name validation and set  = {}", body.getCustomerName());
            customer.setCustomerName(body.getCustomerName());
        }

        if (!StringUtil.isEmpty(body.getDateOfBirth())) {
            logger.info(" MapCustomerDetails.updateCustomer:: check date of birth validation and set  = {}", body.getDateOfBirth());
            customer.setDateOfBirth(body.getDateOfBirth());
        }

        if (!StringUtil.isEmpty(body.getAccountType())){
            logger.info(" MapCustomerDetails.updateCustomer:: check account type validation and set  = {}", body.getAccountType());
            customer.setAccountType(body.getAccountType());
        }

        if (!StringUtil.isEmpty(body.getProductType())) {
            logger.info(" MapCustomerDetails.updateCustomer:: check product type validation and set  = {}", body.getProductType());
            customer.setProductType(body.getProductType());
        }

        if (!StringUtil.isEmpty(body.getDateOfOpening())){
            logger.info(" MapCustomerDetails.updateCustomer:: check date of opening validation and set  = {}", body.getDateOfOpening());
            customer.setDateOfOpening(body.getDateOfOpening());
        }
        return customer;
    }
}
