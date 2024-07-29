package com.sheikhimtiaz.ecomm.controller;

import com.sheikhimtiaz.ecomm.model.dto.MaxSaleDayDto;
import com.sheikhimtiaz.ecomm.model.dto.TotalSalesDto;
import com.sheikhimtiaz.ecomm.service.SalesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@RestController
@RequestMapping("/api/sales")
public class SalesController {
    private static final Logger log = LoggerFactory.getLogger(SalesController.class);
    @Autowired
    private SalesService salesService;

    @Operation(summary = "The total sale amount of the current day.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request processed successfully"),
            @ApiResponse(responseCode = "304", description = "Not modified"),
            @ApiResponse(responseCode = "404", description = "Content not found",
                    content = @Content),
            @ApiResponse(responseCode = "422", description = "Invalid data has been passed in parameter",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })
    @GetMapping("/today")
    public ResponseEntity<TotalSalesDto> getTotalSalesToday() {
        return ResponseEntity.ok(salesService.getTotalSalesToday());
    }

    @Operation(summary = "The max sale day of a certain time range.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request processed successfully"),
            @ApiResponse(responseCode = "304", description = "Not modified"),
            @ApiResponse(responseCode = "404", description = "Content not found",
                    content = @Content),
            @ApiResponse(responseCode = "422", description = "Invalid data has been passed in parameter",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })
    @GetMapping("/max-day")
    public ResponseEntity<?> getMaxSaleDay(@RequestParam String start, @RequestParam String end) {
        try {
            // Validate and parse dates
            LocalDate startDate = LocalDate.parse(start);
            LocalDate endDate = LocalDate.parse(end);

            // Check if start date is before or equal to end date
            if (startDate.isAfter(endDate)) {
                return ResponseEntity.badRequest().body("Start date must be before or equal to end date.");
            }

            // Call service to get max sale day
            MaxSaleDayDto maxSaleDay = salesService.getMaxSaleDay(startDate, endDate);
            return ResponseEntity.ok(maxSaleDay);
        } catch (DateTimeParseException e) {
            // Handle invalid date format
            return ResponseEntity.badRequest().body("Invalid date format. Please use yyyy-MM-dd.");
        } catch (Exception e) {
            // Handle other unexpected exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }
}
