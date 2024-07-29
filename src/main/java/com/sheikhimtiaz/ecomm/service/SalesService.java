package com.sheikhimtiaz.ecomm.service;

import com.sheikhimtiaz.ecomm.model.dto.MaxSaleDayDto;
import com.sheikhimtiaz.ecomm.model.dto.TotalSalesDto;
import com.sheikhimtiaz.ecomm.model.entity.Sale;
import com.sheikhimtiaz.ecomm.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.math.BigDecimal;

@Service
public class SalesService {

    @Autowired
    private SaleRepository saleRepository;

    public TotalSalesDto getTotalSalesToday() {
        LocalDate today = LocalDate.now();
        List<Sale> sales = saleRepository.findByDate(today);

        BigDecimal totalSalesAmount = sales.stream()
                .map(Sale::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        TotalSalesDto totalSalesDto = new TotalSalesDto();
        totalSalesDto.setTotalSalesAmount(totalSalesAmount);
        return totalSalesDto;
    }

    public MaxSaleDayDto getMaxSaleDay(LocalDate startDate, LocalDate endDate) {

        List<Sale> sales = saleRepository.findByDateBetween(startDate, endDate);

        String maxSaleDay = sales.stream()
                .collect(Collectors.groupingBy(
                        Sale::getDate,
                        Collectors.reducing(BigDecimal.ZERO, Sale::getTotalAmount, BigDecimal::add))
                )
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .map(LocalDate::toString)
                .orElse(""); // Return an empty string if no data is found

        MaxSaleDayDto maxSaleDayDto = new MaxSaleDayDto();
        maxSaleDayDto.setMaxSaleDay(maxSaleDay);
        return maxSaleDayDto;
    }
}
