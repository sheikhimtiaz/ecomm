package com.sheikhimtiaz.ecomm.service;

import com.sheikhimtiaz.ecomm.model.entity.Customer;
import com.sheikhimtiaz.ecomm.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCustomerById_ValidId() {
        Long customerId = 1L;
        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setName("Alice");

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        Optional<Customer> result = customerService.getCustomerById(customerId);

        assertTrue(result.isPresent());
        assertEquals(customerId, result.get().getId());
        assertEquals("Alice", result.get().getName());

        verify(customerRepository, times(1)).findById(customerId);
    }

    @Test
    void testGetCustomerById_NonExistentId() {
        Long customerId = 2L;

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        Optional<Customer> result = customerService.getCustomerById(customerId);

        assertFalse(result.isPresent());

        verify(customerRepository, times(1)).findById(customerId);
    }

    @Test
    void testGetCustomerById_NullId() {
        Long customerId = null;

        Optional<Customer> result = customerService.getCustomerById(customerId);

        assertFalse(result.isPresent());
    }
}
