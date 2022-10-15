package com.psl.repository;

import com.psl.entity.Customer;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import java.util.List;

/**
 * DataBase Operation for CRUD
 */
@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    @Query(value="SELECT * FROM customer s WHERE s.account_number = :accountNumber",
            nativeQuery = true)
    List<Customer> findByAccountNumber(String accountNumber);
    @Query(value="SELECT * FROM customer s WHERE s.mobile_number = :mobileNumber",
            nativeQuery = true)
    List<Customer> findByMobileNumber(String mobileNumber);
    @Query(value="SELECT * FROM customer s WHERE s.pan_number = :panNumber",
            nativeQuery = true)
    List<Customer> findByPanNumber(String panNumber);
    @Query(value="SELECT * FROM customer s WHERE s.account_type = :accountType",
            nativeQuery = true)
    List<Customer> findByAccountType(String accountType);
    @Query(value="SELECT * FROM customer s WHERE s.product_type = :productType",
            nativeQuery = true)
    List<Customer> findByProductType(String productType);
    @Query(value="SELECT * FROM customer s WHERE s.date_of_opening = :dateOfOpening",
            nativeQuery = true)
    List<Customer> findByDateOfOpening(String dateOfOpening);
    @Query(value="SELECT * FROM customer s WHERE LOWER(s.customer_name)  like CONCAT('%', LOWER(:customerName), '%') and s.date_of_birth=:dob",
            nativeQuery = true)
    List<Customer> findByCustomerNameAndDateOfBirth(String customerName, String dob);
}
