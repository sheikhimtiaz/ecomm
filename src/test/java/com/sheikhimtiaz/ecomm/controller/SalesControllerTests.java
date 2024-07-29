package com.sheikhimtiaz.ecomm.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SalesControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetTotalSalesToday() throws Exception {
        mockMvc.perform(get("/api/sales/today"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalSalesAmount").isNumber());
    }

    @Test
    void testGetTotalSalesToday_NoSales() throws Exception {
        // Setup: Ensure no sales are recorded for today
        mockMvc.perform(get("/api/sales/today"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalSalesAmount").value(0.0));
    }

    @Test
    void testGetMaxSaleDay() throws Exception {
        mockMvc.perform(get("/api/sales/max-day?start=2024-01-01&end=2024-01-31"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maxSaleDay").isString());
    }

    @Test
    void testGetMaxSaleDay_InvalidDateRange() throws Exception {
        mockMvc.perform(get("/api/sales/max-day?start=2024-01-31&end=2024-01-01"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetMaxSaleDay_NoSalesInRange() throws Exception {
        mockMvc.perform(get("/api/sales/max-day?start=2024-02-01&end=2024-02-28")) // Assume no sales in February
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maxSaleDay").value(""));
    }
}
