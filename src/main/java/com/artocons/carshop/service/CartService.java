package com.artocons.carshop.service;

import com.artocons.carshop.persistence.model.Cart;
import com.artocons.carshop.persistence.repository.CartRepository;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Optional;

@Service
public class CartService {

    @Resource
    public CartRepository cartRepository;

    @Autowired
    private HttpSession session;

    public void addItemToCart(Cart cartItem) throws ServiceException {
        try {

            Cart cartQuery = new Cart();
//            cartQuery.setUserId(userId);
            cartQuery.setProductId(cartItem.getProductId());

            Example<Cart> example = Example.of(cartQuery);

            Optional<Cart> existCarts = cartRepository.findOne(example);
            if (existCarts.isPresent()) {
                return;
            }

            cartRepository.save(cartItem);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
