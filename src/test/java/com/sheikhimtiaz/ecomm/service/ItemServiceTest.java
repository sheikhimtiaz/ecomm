package com.sheikhimtiaz.ecomm.service;


import com.sheikhimtiaz.ecomm.model.dto.ItemDto;
import com.sheikhimtiaz.ecomm.model.entity.Item;
import com.sheikhimtiaz.ecomm.model.entity.Sale;
import com.sheikhimtiaz.ecomm.repository.ItemRepository;
import com.sheikhimtiaz.ecomm.repository.SaleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private SaleRepository saleRepository;

    @InjectMocks
    private ItemService itemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetTopSellingItemsAllTime_WithSales() {
        LocalDate startDate = LocalDate.now().minusYears(100);
        Item item1 = new Item();
        item1.setId(1L);
        item1.setName("Laptop");
        item1.setPrice(BigDecimal.valueOf(1200));
        item1.setSales(List.of(new Sale()));

        Item item2 = new Item();
        item2.setId(2L);
        item2.setName("Smartphone");
        item2.setPrice(BigDecimal.valueOf(800));
        item2.setSales(List.of(new Sale()));

        when(itemRepository.findAll()).thenReturn(List.of(item1, item2));
        when(saleRepository.findSalesByDateBetween(startDate, LocalDate.now())).thenReturn(List.of(
                new Sale(1L, item1, LocalDate.now(), 1, BigDecimal.valueOf(1200)),
                new Sale(2L, item2, LocalDate.now(), 1, BigDecimal.valueOf(800))
        ));

        List<ItemDto> topSellingItems = itemService.getTopSellingItemsAllTime();

        assertNotNull(topSellingItems);
        assertEquals(2, topSellingItems.size());
        assertEquals("Laptop", topSellingItems.get(0).getName());
        assertEquals("Smartphone", topSellingItems.get(1).getName());
    }

    @Test
    void testGetTopSellingItemsLastMonth_WithSales() {
        LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);
        Item item1 = new Item();
        item1.setId(1L);
        item1.setName("Laptop");
        item1.setPrice(BigDecimal.valueOf(800));
        item1.setSales(List.of(new Sale()));

        Item item2 = new Item();
        item2.setId(2L);
        item2.setName("Smartphone");
        item2.setPrice(BigDecimal.valueOf(800));
        item2.setSales(List.of(new Sale()));

        when(saleRepository.findSalesByDateBetween(oneMonthAgo, LocalDate.now())).thenReturn(List.of(
                new Sale(1L, item1, LocalDate.now(), 1, BigDecimal.valueOf(800)),
                new Sale(1L, item1, LocalDate.now(), 1, BigDecimal.valueOf(800)),
                new Sale(2L, item2, LocalDate.now(), 5, BigDecimal.valueOf(4000))
        ));

        List<ItemDto> topSellingItems = itemService.getTopSellingItemsLastMonth();

        assertNotNull(topSellingItems);
        assertEquals(2, topSellingItems.size());
        assertEquals("Smartphone", topSellingItems.get(0).getName());
        assertEquals("Laptop", topSellingItems.get(1).getName());
    }

    @Test
    void testGetTopSellingItemsLastMonth_NoSales() {
        LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);

        when(saleRepository.findSalesByDateBetween(oneMonthAgo, LocalDate.now())).thenReturn(Collections.emptyList());

        List<ItemDto> topSellingItems = itemService.getTopSellingItemsLastMonth();

        assertNotNull(topSellingItems);
        assertEquals(0, topSellingItems.size());
    }
}
