package com.artocons.carshop.service;

import com.artocons.carshop.persistence.model.Cart;
import com.artocons.carshop.persistence.model.ResultData;
import com.artocons.carshop.persistence.repository.CartRepository;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Cart> getCartList() {
        List<Cart> cart = (List<Cart>) session.getAttribute("cart");

        if (CollectionUtils.isEmpty(cart)){
            Cart cartItem = new Cart();
            cart = new ArrayList<Cart>();
            cart.add(cartItem);
        }
        return cart;
    }

    public ResultData addItemToCart(Cart cartNew) throws ServiceException {
        try {
            List<Cart> cartOldItms = (List<Cart>) session.getAttribute("cart");

            Cart existCart = cartOldItms.stream()
                    .filter(item -> item.getProduct().equals(cartNew.getProduct()))
                    .findAny()
                    .orElse(null);

            if (existCart == null) {
                session.setAttribute("cart", cartNew);
                return new ResultData(getCartCount(), getCartTotalCost());
            }

//            cartRepository.save(cartNew);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return null;
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
            productIds.add(cartItem.getProduct());
        }

        BigDecimal totalCost = BigDecimal.valueOf(0);
        for (Cart cartItem : cart) {
            if ( productIds.contains(cartItem.getProduct())){
                BigDecimal cost = BigDecimal.valueOf(cartItem.getQuantity()).multiply(carService.getPriceById(cartItem.getProduct()));
                totalCost.add(cost);
            }
        }
        return totalCost;
    }
}
