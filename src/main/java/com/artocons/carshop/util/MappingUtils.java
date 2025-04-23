package com.artocons.carshop.util;

import com.artocons.carshop.exception.ResourceNotFoundException;
import com.artocons.carshop.persistence.dtos.CartDTO;
import com.artocons.carshop.persistence.dtos.CartItemDTO;
import com.artocons.carshop.persistence.model.Car;
import com.artocons.carshop.persistence.model.Cart;
import com.artocons.carshop.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MappingUtils {

    private static CartService cartService;
    //entity -> dto
    public static CartItemDTO convertToCartItemDTO(Car product, Cart cart) {
        CartItemDTO cartItemDto = new CartItemDTO();
        cartItemDto.setProduct(cart.getProduct());
        cartItemDto.setQuantity(cart.getQuantity());
        cartItemDto.setDescription(cart.getDescription());
        cartItemDto.setBrand(product.getBrand());
        cartItemDto.setModel(product.getModel());
        cartItemDto.setProductionYear(product.getProductionYear());
        cartItemDto.setPrice(product.getPrice());
        cartItemDto.setColors(product.getColors());
//        cartService.addProductInfo((List<CartItemDTO>) dto);

        return cartItemDto;
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
