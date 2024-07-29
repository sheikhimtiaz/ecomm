package com.sheikhimtiaz.ecomm.controller;

import com.sheikhimtiaz.ecomm.model.dto.ItemDto;
import com.sheikhimtiaz.ecomm.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ItemControllerTests {

    private MockMvc mockMvc;

    @Mock
    private ItemService itemService;

    @InjectMocks
    private ItemController itemController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();
    }

    @Test
    void testGetTopSellingItemsAllTime() throws Exception {
        List<ItemDto> items = Arrays.asList(
                new ItemDto(1L, "Laptop", BigDecimal.valueOf(1200.00), 1200.00, 1L),
                new ItemDto(2L, "Smartphone", BigDecimal.valueOf(800.00), 800.00, 1L)
        );

        when(itemService.getTopSellingItemsAllTime()).thenReturn(items);

        mockMvc.perform(get("/api/items/top/all-time").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Laptop"))
                .andExpect(jsonPath("$[0].totalSalesAmount").value(1200.00))
                .andExpect(jsonPath("$[1].name").value("Smartphone"))
                .andExpect(jsonPath("$[1].totalSalesAmount").value(800.00));
    }

    @Test
    void testGetTopSellingItemsLastMonth() throws Exception {
        List<ItemDto> items = Arrays.asList(
                new ItemDto(1L, "Laptop", BigDecimal.valueOf(1200.00), 1200.00, 1L),
                new ItemDto(2L, "Smartphone", BigDecimal.valueOf(800.00), 800.00, 1L)
        );

        when(itemService.getTopSellingItemsLastMonth()).thenReturn(items);

        mockMvc.perform(get("/api/items/top/last-month").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Laptop"))
                .andExpect(jsonPath("$[0].totalSalesAmount").value(1200.00))
                .andExpect(jsonPath("$[1].name").value("Smartphone"))
                .andExpect(jsonPath("$[1].totalSalesAmount").value(800.00));
    }
}
