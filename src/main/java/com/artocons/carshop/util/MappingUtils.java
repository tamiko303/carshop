package com.artocons.carshop.util;

import com.artocons.carshop.persistence.dtos.CartItemDTO;
import com.artocons.carshop.persistence.dtos.OrderHeaderDTO;
import com.artocons.carshop.persistence.dtos.OrderItemDTO;
import com.artocons.carshop.persistence.model.Cart;
import com.artocons.carshop.persistence.model.OrderHeader;
import com.artocons.carshop.persistence.model.OrderItem;
import com.artocons.carshop.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public static OrderItemDTO convertToOrderItemDTO(OrderItem orderItem) {
        OrderItemDTO dto = new OrderItemDTO(orderItem.getProduct().getId(), orderItem.getQuantity());

        return dto;
    }

    public static OrderHeaderDTO convertToOrderHeaderDTO(OrderHeader order, String message) {
        OrderHeaderDTO dto = new OrderHeaderDTO();
        dto.setOrderId(order.getOrderId());
        dto.setSubTotal(order.getSubTotal());
        dto.setDelivery(order.getDelivery());
        dto.setTotal(order.getTotal());

        dto.setFirstName(order.getFirstName());
        dto.setLastName(order.getLastName());
        dto.setAdress(order.getAdress());
        dto.setPhone(order.getPhone());
        dto.setDescription(order.getDescription());

        dto.setMessage(message);

        dto.setItems(order.getOrderItems());

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

    public static OrderItem convertToOrderItemEntity(OrderItemDTO orderItemDTO) {
        OrderItem entity = new OrderItem();
//        entity.setProduct(orderItemDTO.getProductId());   //(orderItemDTO.getProductId());
//        entity.setQuantity(orderItemDTO.getQuantity());

        return entity;
    }
}
