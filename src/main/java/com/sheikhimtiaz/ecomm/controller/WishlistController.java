package com.sheikhimtiaz.ecomm.controller;

import com.sheikhimtiaz.ecomm.model.entity.Customer;
import com.sheikhimtiaz.ecomm.service.CustomerService;
import com.sheikhimtiaz.ecomm.service.WishlistService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {
    private static final Logger log = LoggerFactory.getLogger(WishlistController.class);
    @Autowired private WishlistService wishlistService;

    @Autowired private CustomerService customerService;


    @Operation(summary = "The wish list of a customer.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request processed successfully"),
            @ApiResponse(responseCode = "304", description = "Not modified"),
            @ApiResponse(responseCode = "404", description = "Content not found",
                    content = @Content),
            @ApiResponse(responseCode = "422", description = "Invalid data has been passed in parameter",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })
    @GetMapping("/{customerId}")
    public ResponseEntity<?> getWishlist(@PathVariable Long customerId) {
        Optional<Customer> customer = customerService.getCustomerById(customerId);

//         Check if customer does not exist
        if (Objects.isNull(customer) || customer.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(wishlistService.getWishlist(customerId));
    }
}
