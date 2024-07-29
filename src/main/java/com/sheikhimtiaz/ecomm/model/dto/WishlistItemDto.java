package com.sheikhimtiaz.ecomm.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WishlistItemDto {

    private Long itemId;
    private String itemName;
    private BigDecimal itemPrice;


    @Override
    public String toString() {
        return "WishlistItemDto{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", itemPrice=" + itemPrice +
                '}';
    }
}
