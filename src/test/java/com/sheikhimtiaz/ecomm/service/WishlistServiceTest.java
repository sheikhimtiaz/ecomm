package com.sheikhimtiaz.ecomm.service;

import com.sheikhimtiaz.ecomm.model.dto.WishlistDto;
import com.sheikhimtiaz.ecomm.model.dto.WishlistItemDto;
import com.sheikhimtiaz.ecomm.model.entity.Customer;
import com.sheikhimtiaz.ecomm.model.entity.Item;
import com.sheikhimtiaz.ecomm.model.entity.Wishlist;
import com.sheikhimtiaz.ecomm.repository.WishlistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class WishlistServiceTest {

    @Mock
    private WishlistRepository wishlistRepository;

    @InjectMocks
    private WishlistService wishlistService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetWishlistForValidCustomer() {
        Long customerId = 1L;
        List<Wishlist> wishlistItems = new ArrayList<>();
        Item item1 = new Item(1L, "Laptop", BigDecimal.valueOf(1200.00), null);
        Item item2 = new Item(2L, "Smartphone", BigDecimal.valueOf(800.00), null);

        wishlistItems.add(new Wishlist(1L, new Customer(customerId, "Alice", null), item1));
        wishlistItems.add(new Wishlist(2L, new Customer(customerId, "Alice", null), item2));

        when(wishlistRepository.findByCustomerId(customerId)).thenReturn(wishlistItems);

        WishlistDto wishlistDto = wishlistService.getWishlist(customerId);

        assertNotNull(wishlistDto);
        assertEquals(2, wishlistDto.getWishlistItems().size());

        WishlistItemDto itemDto1 = wishlistDto.getWishlistItems().get(0);
        assertEquals(1L, itemDto1.getItemId());
        assertEquals("Laptop", itemDto1.getItemName());
        assertEquals(BigDecimal.valueOf(1200.00), itemDto1.getItemPrice());

        WishlistItemDto itemDto2 = wishlistDto.getWishlistItems().get(1);
        assertEquals(2L, itemDto2.getItemId());
        assertEquals("Smartphone", itemDto2.getItemName());
        assertEquals(BigDecimal.valueOf(800.00), itemDto2.getItemPrice());
    }

    @Test
    void testGetWishlistForNonExistentCustomer() {
        Long customerId = 999L;

        when(wishlistRepository.findByCustomerId(customerId)).thenReturn(new ArrayList<>());

        WishlistDto wishlistDto = wishlistService.getWishlist(customerId);

        assertNotNull(wishlistDto);
        assertTrue(wishlistDto.getWishlistItems().isEmpty());
    }
}
