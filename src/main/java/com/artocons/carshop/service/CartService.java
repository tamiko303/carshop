package com.artocons.carshop.service;

import com.artocons.carshop.persistence.model.Cart;
import com.artocons.carshop.persistence.repository.CartRepository;
import org.hibernate.service.spi.ServiceException;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Optional;

@Service
public class CartService {

    @Resource
    public CartRepository cartRepository;

    public void addItemToCart(long userId, long productId, int quantity, Optional<String> description) throws ServiceException {
        try {

            Cart cartQuery = new Cart();
            cartQuery.setUserId(userId);
            cartQuery.setProductId(productId);

            Example<Cart> example = Example.of(cartQuery);

            Optional<Cart> existCarts = cartRepository.findOne(example);
            if (existCarts.isPresent()) {
                return;
            }

            String descr = description.orElse("");
            Cart cartItem = buildCart(productId, userId, quantity, descr);
            cartRepository.save(cartItem);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private Cart buildCart(long userId, long productId, int quantity, String description) {
        Cart cartItem = new Cart();

        cartItem.setUserId(userId);
        cartItem.setProductId(productId);
        cartItem.setQuantity(quantity);
        cartItem.setDate(new Date());
        cartItem.setDescription(description);

        return cartItem;
    }
}
