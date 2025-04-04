package com.artocons.carshop.util;

import com.artocons.carshop.persistence.dtos.CartItemDTO;
import com.artocons.carshop.persistence.model.Cart;
import com.artocons.carshop.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MappingUtils {

    private final CartService cartService;
    //entity -> dto
    public static CartItemDTO convertToCartItemDTO(Cart cart) {
        CartItemDTO dto = new CartItemDTO();
        dto.setProduct(cart.getProduct());
        dto.setQuantity(cart.getQuantity());
        dto.setDescription(cart.getDescription());

        return dto;
    }

    //dto -> entity
    public static Cart convertToCartItemEntity(CartItemDTO cartItemDTO) {
        Cart entity = new Cart();
        entity.setProduct(cartItemDTO.getProduct());
        entity.setQuantity(cartItemDTO.getQuantity());
        entity.setDescription(cartItemDTO.getDescription());

        return entity;
    }
}
