package com.artocons.carshop.service;

import com.artocons.carshop.persistence.model.Car;
import com.artocons.carshop.persistence.model.Cart;
import com.artocons.carshop.persistence.model.Stock;
import com.artocons.carshop.persistence.repository.CartRepository;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

@Service
public class CartService {

    @Resource
    public CartRepository cartRepository;

    @Autowired
    private HttpSession session;
    @Autowired
    private CarService carService;

    public void addItemToCart(Cart cartItem) throws ServiceException {
        try {
            String userId = (String) session.getAttribute("user");
            Cart cartQuery = new Cart();
//            cartQuery.setUserId(userId);
            cartQuery.setProductId(cartItem.getProductId());

            Example<Cart> example = Example.of(cartQuery);

            Optional<Cart> existCarts = cartRepository.findOne(example);
            if (existCarts.isPresent()) {
//                TODO
            }

            cartRepository.save(cartItem);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public int getCartCount() {
        List<Cart> cart = (List<Cart>) session.getAttribute("cart");

        if (CollectionUtils.isEmpty(cart)){
            return 0;
        }
        return cart.size();
    }

    public BigDecimal getCartTotalCost() {
        List<Cart> cart = (List<Cart>) session.getAttribute("cart");

        if (CollectionUtils.isEmpty(cart)){
            return BigDecimal.valueOf(0);
        };

        Set<Long> productIds = new HashSet<>();

        for (Cart cartItem : cart) {
            productIds.add(cartItem.getProductId());
        }

        BigDecimal totalCost = BigDecimal.valueOf(0);
        for (Cart cartItem : cart) {
            if ( productIds.contains(cartItem.getProductId())){
                BigDecimal cost = BigDecimal.valueOf(cartItem.getQuantity()).multiply(carService.getPriceById(cartItem.getProductId()));
                totalCost.add(cost);
            }
        }
        return totalCost;
    }
}
