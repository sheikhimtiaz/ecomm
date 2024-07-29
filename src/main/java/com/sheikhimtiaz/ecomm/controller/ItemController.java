package com.sheikhimtiaz.ecomm.controller;

import com.sheikhimtiaz.ecomm.model.dto.ItemDto;
import com.sheikhimtiaz.ecomm.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {
    private static final Logger log = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private ItemService itemService;

    @Operation(summary = "Top 5 selling items of all time (based on total sale amount).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request processed successfully"),
            @ApiResponse(responseCode = "304", description = "Not modified"),
            @ApiResponse(responseCode = "404", description = "Content not found",
                    content = @Content),
            @ApiResponse(responseCode = "422", description = "Invalid data has been passed in parameter",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })
    @GetMapping("/top/all-time")
    public ResponseEntity<List<ItemDto>> getTopSellingItemsAllTime() {
        return ResponseEntity.ok(itemService.getTopSellingItemsAllTime());
    }

    @Operation(summary = "Top 5 selling items of the last month (based on number of sales).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request processed successfully"),
            @ApiResponse(responseCode = "304", description = "Not modified"),
            @ApiResponse(responseCode = "404", description = "Content not found",
                    content = @Content),
            @ApiResponse(responseCode = "422", description = "Invalid data has been passed in parameter",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })
    @GetMapping("/top/last-month")
    public ResponseEntity<List<ItemDto>> getTopSellingItemsLastMonth() {
        return ResponseEntity.ok(itemService.getTopSellingItemsLastMonth());
    }
}
