package com.sheikhimtiaz.ecomm.service;

import com.sheikhimtiaz.ecomm.model.dto.MaxSaleDayDto;
import com.sheikhimtiaz.ecomm.model.dto.TotalSalesDto;
import com.sheikhimtiaz.ecomm.model.entity.Sale;
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
public class SalesServiceTest {

    @Mock
    private SaleRepository saleRepository;

    @InjectMocks
    private SalesService salesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetTotalSalesToday_WithSales() {
        LocalDate today = LocalDate.now();
        Sale sale1 = new Sale();
        sale1.setTotalAmount(BigDecimal.valueOf(1200));
        Sale sale2 = new Sale();
        sale2.setTotalAmount(BigDecimal.valueOf(800));

        when(saleRepository.findByDate(today)).thenReturn(List.of(sale1, sale2));

        TotalSalesDto totalSalesDto = salesService.getTotalSalesToday();

        assertNotNull(totalSalesDto);
        assertEquals(BigDecimal.valueOf(2000), totalSalesDto.getTotalSalesAmount());

        verify(saleRepository, times(1)).findByDate(today);
    }

    @Test
    void testGetTotalSalesToday_NoSales() {
        LocalDate today = LocalDate.now();

        when(saleRepository.findByDate(today)).thenReturn(Collections.emptyList());

        TotalSalesDto totalSalesDto = salesService.getTotalSalesToday();

        assertNotNull(totalSalesDto);
        assertEquals(BigDecimal.ZERO, totalSalesDto.getTotalSalesAmount());

        verify(saleRepository, times(1)).findByDate(today);
    }

    @Test
    void testGetMaxSaleDay_WithSalesWithinDateRange() {
        LocalDate startDate = LocalDate.of(2024, 7, 1);
        LocalDate endDate = LocalDate.of(2024, 7, 3);
        Sale sale1 = new Sale();
        sale1.setDate(LocalDate.of(2024, 7, 1));
        sale1.setTotalAmount(BigDecimal.valueOf(1200));
        Sale sale2 = new Sale();
        sale2.setDate(LocalDate.of(2024, 7, 2));
        sale2.setTotalAmount(BigDecimal.valueOf(800));
        Sale sale3 = new Sale();
        sale3.setDate(LocalDate.of(2024, 7, 1));
        sale3.setTotalAmount(BigDecimal.valueOf(150));

        when(saleRepository.findByDateBetween(startDate, endDate)).thenReturn(List.of(sale1, sale2, sale3));

        MaxSaleDayDto maxSaleDayDto = salesService.getMaxSaleDay(startDate, endDate);

        assertNotNull(maxSaleDayDto);
        assertEquals("2024-07-01", maxSaleDayDto.getMaxSaleDay());

        verify(saleRepository, times(1)).findByDateBetween(startDate, endDate);
    }

    @Test
    void testGetMaxSaleDay_NoSalesWithinDateRange() {
        LocalDate startDate = LocalDate.of(2024, 7, 1);
        LocalDate endDate = LocalDate.of(2024, 7, 3);

        when(saleRepository.findByDateBetween(startDate, endDate)).thenReturn(Collections.emptyList());

        MaxSaleDayDto maxSaleDayDto = salesService.getMaxSaleDay(startDate, endDate);

        assertNotNull(maxSaleDayDto);
        assertEquals("", maxSaleDayDto.getMaxSaleDay());

        verify(saleRepository, times(1)).findByDateBetween(startDate, endDate);
    }

    @Test
    void testGetMaxSaleDay_InvalidDateRange() {
        LocalDate startDate = LocalDate.of(2024, 7, 4);
        LocalDate endDate = LocalDate.of(2024, 7, 1);

        MaxSaleDayDto maxSaleDayDto = salesService.getMaxSaleDay(startDate, endDate);

        assertNotNull(maxSaleDayDto);
        assertEquals("", maxSaleDayDto.getMaxSaleDay());

        verify(saleRepository, times(1)).findByDateBetween(startDate, endDate);
    }
}
