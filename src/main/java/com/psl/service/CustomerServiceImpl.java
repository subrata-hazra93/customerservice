package com.psl.service;

import com.psl.constant.ConstantMessage;
import com.psl.exception.CustomException;
import com.psl.repository.CustomerRepository;
import com.psl.entity.Customer;
import com.psl.mapper.MapCustomerDetails;
import com.psl.util.CustomerFieldsValidator;
import com.psl.util.CustomerValidation;
import com.psl.util.StringUtil;
import io.micronaut.http.HttpStatus;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation class for customer CRUD Operation
 */
@Singleton
public class CustomerServiceImpl implements CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
    @Inject
    CustomerRepository customerRepository;

    /**
     * Create Customer Data and store it to the database
     *
     * @param request
     * @return Customer
     */
    @Override
    public Customer createCustomer(Customer request) throws CustomException {
        logger.info("CustomerServiceImpl.createCustomer :: create customer object in database  = {}", request);
        CustomerValidation.isAllCustomerDetailsPresent(request);
        CustomerFieldsValidator.validateCustomerRequestFormat(request);
        validateDuplicateAccountAndPan(request);
        return customerRepository.save(request);
    }

    /**
     * Delete Customer data from Database
     *
     * @param id
     * @return void
     */
    @Override
    public void deleteCustomerDetails(Long id) {
        logger.info("CustomerServiceImpl.deleteCustomerDetails :: delete customer object from database  = {}", id);
        Customer response = customerRepository.findById(id).orElseThrow(() -> new CustomException(ConstantMessage.DELETE_NO_DATA, HttpStatus.NOT_FOUND));
        customerRepository.delete(response);
    }

    /**
     * Search Customer data from Database
     *
     * @param fields
     * @return List<Customer>
     */
    @Override
    public List<Customer> getListOfCustomer(Customer fields) throws CustomException {
        logger.info("CustomerServiceImpl.getListOfCustomer :: list of customer by searching with multiple search criteria = {}", fields);
        CustomerValidation.isAllCustomerDetailsAreEmpty(fields);
        CustomerFieldsValidator.validateCustomerRequestFormat(fields);
        CustomerValidation.isValidCustomerNameAndDob(fields);
        List<Customer> daoResponse = new ArrayList<>();
        return getCustomerList(fields, daoResponse);
    }

    /**
     * Update Customer data on Database
     *
     * @param body
     * @return CustomerDetailsDto
     */
    @Override
    public Customer updateCustomer(Long id, Customer body) {
        logger.info("CustomerServiceImpl.updateCustomer :: update customer object fields from database by passing id and body  = {}", body);
        Customer customerDetails = customerRepository.findById(id).orElseThrow(() -> new CustomException(ConstantMessage.UPDATE_NO_DATA, HttpStatus.NOT_FOUND));
        CustomerValidation.isAllCustomerDetailsAreEmpty(body);
        MapCustomerDetails.updateCustomer(body, customerDetails);
        try{
            customerDetails = customerRepository.update(customerDetails);
        }catch(Exception exception){
            logger.error(exception.toString());
            throw new CustomException(ConstantMessage.ACCOUNT_PAN_EXISTS, HttpStatus.BAD_REQUEST);
        }
        return customerDetails;
    }

    /**
     * Search Customer data from Database by id
     *
     * @param id
     * @return CustomerDetailsDto
     */
    @Override
    public Customer retrieveCustomer(Long id) {
        logger.info("CustomerServiceImpl.retrieveCustomer :: retrieve customer object from database by id  = {}", id);
        return customerRepository.findById(id).orElseThrow(() -> new CustomException(ConstantMessage.CUSTOMER_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    /**
     * Check all the parameters of Customer and search as per filter priority
     * And calling repository according to the priority
     */
    List<Customer> getCustomerList(Customer fields, List<Customer> daoResponse) {
        if (!StringUtil.isEmpty(fields.getAccountNumber())) {
            logger.info("CustomerServiceImpl.getCustomerList :: dao search operation for valid account number  = {}",
                    fields.getAccountNumber());
            daoResponse = customerRepository.findByAccountNumber(fields.getAccountNumber());
        } else if (!StringUtil.isEmpty(fields.getMobileNumber())) {
            logger.info("CustomerServiceImpl.getCustomerList :: dao search operation for valid mobile number  = {}",
                    fields.getMobileNumber());
            daoResponse = customerRepository.findByMobileNumber(fields.getMobileNumber());
        } else if (!StringUtil.isEmpty(fields.getPanNumber())) {
            logger.info("CustomerServiceImpl.getCustomerList :: dao search operation for valid pan number  = {}",
                    fields.getPanNumber());
            daoResponse = customerRepository.findByPanNumber(fields.getPanNumber());
        } else if (!StringUtil.isEmpty(fields.getCustomerName()) && !StringUtil.isEmpty(fields.getDateOfBirth())) {
            logger.info("CustomerServiceImpl.getCustomerList :: dao search operation for valid customer name and date of birth  = {}",
                    fields.getCustomerName());
            daoResponse = customerRepository.findByCustomerNameAndDateOfBirth(fields.getCustomerName(), fields.getDateOfBirth());
        } else if (!StringUtil.isEmpty(fields.getAccountType())) {
            logger.info("CustomerServiceImpl.getCustomerList :: dao search operation for valid account type  = {}",
                    fields.getAccountType());
            daoResponse = customerRepository.findByAccountType(fields.getAccountType());
        } else if (!StringUtil.isEmpty(fields.getProductType())) {
            logger.info("CustomerServiceImpl.getCustomerList :: dao search operation for valid product type  = {}",
                    fields.getProductType());
            daoResponse = customerRepository.findByProductType(fields.getProductType());
        } else if (!StringUtil.isEmpty(fields.getDateOfOpening())) {
            logger.info("CustomerServiceImpl.getCustomerList :: dao search operation for valid date of opening  = {}",
                    fields.getDateOfOpening());
            daoResponse = customerRepository.findByDateOfOpening(fields.getDateOfOpening());
        }
        if (daoResponse.isEmpty()) {
            logger.error("CustomerServiceImpl.getCustomerList :: throw error if all the validation failed  = {}",
                    fields.getAccountNumber());
            throw new CustomException(ConstantMessage.CUSTOMER_DATA_NOT_AVAILABLE, HttpStatus.NOT_FOUND);
        }
        return daoResponse;
    }

    /**
     * Check account number and/or pan number is duplicate or not
     */
    void validateDuplicateAccountAndPan(Customer customer){
        logger.error("CustomerServiceImpl.validateDuplicateAccountAndPan :: throw error if account number or/and pan number is duplicate or not = {}", customer);
        List<Customer> customerListByAccount=customerRepository.findByAccountNumber(customer.getAccountNumber());
        List<Customer> customerListByPan=customerRepository.findByPanNumber(customer.getPanNumber());
        if(!customerListByAccount.isEmpty() || !customerListByPan.isEmpty()){
            throw new CustomException(ConstantMessage.ACCOUNT_PAN_EXISTS,HttpStatus.BAD_REQUEST);
        }
    }

}
