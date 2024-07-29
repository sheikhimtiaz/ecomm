package com.sheikhimtiaz.ecomm.controller;


import com.sheikhimtiaz.ecomm.model.dto.WishlistDto;
import com.sheikhimtiaz.ecomm.model.dto.WishlistItemDto;
import com.sheikhimtiaz.ecomm.model.entity.Customer;
import com.sheikhimtiaz.ecomm.service.CustomerService;
import com.sheikhimtiaz.ecomm.service.WishlistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class WishlistControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WishlistService wishlistService;

    @MockBean
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetWishlist_ValidCustomerId() throws Exception {
        Long customerId = 1L;

        // Mock Customer
        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setName("Alice");

        // Mock Wishlist Items
        WishlistItemDto item1 = new WishlistItemDto(1L, "Laptop", new BigDecimal("1200.00"));
        WishlistItemDto item2 = new WishlistItemDto(2L, "Smartphone", new BigDecimal("800.00"));
        WishlistDto wishlistDto = new WishlistDto(Arrays.asList(item1, item2));

        when(customerService.getCustomerById(customerId)).thenReturn(Optional.of(customer));
        when(wishlistService.getWishlist(customerId)).thenReturn(wishlistDto);

        mockMvc.perform(get("/api/wishlist/{customerId}", customerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.wishlistItems").isArray())
                .andExpect(jsonPath("$.wishlistItems.length()").value(2))
                .andExpect(jsonPath("$.wishlistItems[0].itemId").value(1))
                .andExpect(jsonPath("$.wishlistItems[0].itemName").value("Laptop"))
                .andExpect(jsonPath("$.wishlistItems[0].itemPrice").value(1200.00))
                .andExpect(jsonPath("$.wishlistItems[1].itemId").value(2))
                .andExpect(jsonPath("$.wishlistItems[1].itemName").value("Smartphone"))
                .andExpect(jsonPath("$.wishlistItems[1].itemPrice").value(800.00));
    }

    @Test
    void testGetWishlist_NonExistentCustomerId() throws Exception {
        Long customerId = 99L;

        when(customerService.getCustomerById(customerId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/wishlist/{customerId}", customerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetWishlist_EmptyWishlist() throws Exception {
        Long customerId = 2L;

        // Mock Customer
        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setName("Bob");

        WishlistDto wishlistDto = new WishlistDto(Arrays.asList());

        when(customerService.getCustomerById(customerId)).thenReturn(Optional.of(customer));
        when(wishlistService.getWishlist(customerId)).thenReturn(wishlistDto);

        mockMvc.perform(get("/api/wishlist/{customerId}", customerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.wishlistItems").isArray())
                .andExpect(jsonPath("$.wishlistItems.length()").value(0));
    }

}
