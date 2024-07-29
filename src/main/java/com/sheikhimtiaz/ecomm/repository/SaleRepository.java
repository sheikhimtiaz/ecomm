package com.sheikhimtiaz.ecomm.repository;

import com.sheikhimtiaz.ecomm.model.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    // Get total sales amount for a specific date
    BigDecimal findTotalSalesAmountByDate(LocalDate date);

    // Find sales within a date range
    List<Sale> findSalesByDateBetween(LocalDate startDate, LocalDate endDate);

    List<Sale> findByDate(LocalDate today);

    List<Sale> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
