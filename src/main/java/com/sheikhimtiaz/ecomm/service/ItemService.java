package com.sheikhimtiaz.ecomm.service;

import com.sheikhimtiaz.ecomm.model.dto.ItemDto;
import com.sheikhimtiaz.ecomm.model.entity.Item;
import com.sheikhimtiaz.ecomm.model.entity.Sale;
import com.sheikhimtiaz.ecomm.repository.ItemRepository;
import com.sheikhimtiaz.ecomm.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private SaleRepository saleRepository;

    /**
     * Retrieves the top 5 selling items of all time based on total sale amount.
     *
     * @return List of top 5 selling items with their total sales amount.
     */
    public List<ItemDto> getTopSellingItemsAllTime() {
        LocalDate startDate = LocalDate.now().minusYears(100);
        List<Item> items = itemRepository.findAll();

        Map<Item, BigDecimal> salesByItem = items.stream()
                .collect(Collectors.toMap(
                        item -> item,
                        item -> saleRepository.findSalesByDateBetween(startDate, LocalDate.now())
                                .stream()
                                .filter(sale -> sale.getItem().equals(item))
                                .map(Sale::getTotalAmount)
                                .reduce(BigDecimal.ZERO, BigDecimal::add)
                ));

        return salesByItem.entrySet().stream()
                .sorted(Map.Entry.<Item, BigDecimal>comparingByValue().reversed())
                .limit(5)
                .map(entry -> new ItemDto(entry.getKey().getId(),
                        entry.getKey().getName(),
                        entry.getValue(),
                        Double.valueOf(entry.getKey().getPrice().toString()),
                        (long) entry.getKey().getSales().size()))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves the top 5 selling items of the last month based on number of sales.
     *
     * @return List of top 5 selling items with their number of sales.
     */
    public List<ItemDto> getTopSellingItemsLastMonth() {
        LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);

        List<Sale> sales = saleRepository.findSalesByDateBetween(oneMonthAgo, LocalDate.now());

        Map<Item, Long> salesCountByItem = sales.stream()
                .collect(Collectors.groupingBy(
                        Sale::getItem,
                        Collectors.summingLong(Sale::getQuantity)
                ));

        return salesCountByItem.entrySet().stream()
                .sorted(Map.Entry.<Item, Long>comparingByValue().reversed())
                .limit(5)
                .map(entry -> new ItemDto(entry.getKey().getId(),
                        entry.getKey().getName(),
                        BigDecimal.valueOf(entry.getValue()),
                        Double.valueOf(entry.getKey().getPrice().toString()),
                        (long) entry.getKey().getSales().size()))
                .collect(Collectors.toList());
    }
}
