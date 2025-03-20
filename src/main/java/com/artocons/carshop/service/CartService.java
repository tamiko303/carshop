package com.artocons.carshop.service;

import com.artocons.carshop.persistence.model.Cart;
import com.artocons.carshop.persistence.model.ResultData;
import com.artocons.carshop.persistence.repository.CartRepository;
import com.artocons.carshop.validation.QuantityValidator;
import org.apache.jasper.tagplugins.jstl.core.If;
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

    private final QuantityValidator quantityValidator;

    public CartService(QuantityValidator quantityValidator) {
        this.quantityValidator = quantityValidator;
    }

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

        Error error = (Error) quantityValidator.validate(cartNew);

        try {

            List<Cart> cartOldItms = (List<Cart>) session.getAttribute("cart");

            if (cartOldItms == null) {
                List<Cart> newCart = new ArrayList<>();
                newCart.add(cartNew);
                session.setAttribute("cart", newCart);
                return new ResultData(getCartCount(), getCartTotalCost());
            }

            Cart existCartItemByProduct = cartOldItms.stream()
                    .filter(item -> item.getProduct().equals(cartNew.getProduct()))
                    .findAny()
                    .orElse(null);

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
        }

        BigDecimal totalCost = BigDecimal.valueOf(0);
        for (Cart cartItem : cart) {
                BigDecimal cost = BigDecimal.valueOf(cartItem.getQuantity()).multiply(carService.getPriceById(cartItem.getProduct()));
                totalCost = totalCost.add(cost);
        }
        return totalCost;
    }
}
