package com.sheikhimtiaz.ecomm.repository;

import com.sheikhimtiaz.ecomm.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
