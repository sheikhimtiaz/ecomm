package com.sheikhimtiaz.ecomm.repository;

import com.sheikhimtiaz.ecomm.model.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    // Find wishlists by customer ID
    List<Wishlist> findByCustomerId(Long customerId);
}
