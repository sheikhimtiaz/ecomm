package com.sheikhimtiaz.ecomm.service;

import com.sheikhimtiaz.ecomm.model.entity.Customer;
import com.sheikhimtiaz.ecomm.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    @Autowired private CustomerRepository customerRepository;

    public Optional<Customer> getCustomerById(Long customerId) {
        return customerRepository.findById(customerId);
    }
}
