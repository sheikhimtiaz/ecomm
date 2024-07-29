package com.sheikhimtiaz.ecomm.service;

import com.sheikhimtiaz.ecomm.model.dto.WishlistDto;
import com.sheikhimtiaz.ecomm.model.dto.WishlistItemDto;
import com.sheikhimtiaz.ecomm.model.entity.Wishlist;
import com.sheikhimtiaz.ecomm.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    public WishlistDto getWishlist(Long customerId) {
        List<Wishlist> wishlistItems = wishlistRepository.findByCustomerId(customerId);

        List<WishlistItemDto> wishlistItemDtos = wishlistItems.stream()
                .map(item -> {
                    WishlistItemDto dto = new WishlistItemDto();
                    dto.setItemId(item.getItem().getId());
                    dto.setItemName(item.getItem().getName());
                    dto.setItemPrice(item.getItem().getPrice());
                    return dto;
                }).collect(Collectors.toList());

        WishlistDto wishlistDto = new WishlistDto();
        wishlistDto.setWishlistItems(wishlistItemDtos);
        return wishlistDto;
    }
}
