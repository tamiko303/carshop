package com.artocons.carshop.util;

import com.artocons.carshop.exception.ResourceNotFoundException;
import com.artocons.carshop.persistence.model.Cart;
import com.artocons.carshop.persistence.model.Stock;
import com.artocons.carshop.persistence.model.Car;
import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CarShopHelper {

    private final HttpSession session;

    public static List<Car> findIntersection(List<Car> cars, List<Stock> stocks) {

        Set<Long> stockIds = stocks.stream()
                .map(Stock::getProductId)
                .collect(Collectors.toSet());

        return cars.stream()
                .filter(c ->stockIds.contains(c.getId()))
                .collect(Collectors.toList());
    }

    public Optional<Cart> getCartItemByProductId(Long productId) throws ResourceNotFoundException {
        List<Cart> cart = (List<Cart>) session.getAttribute("cart");

        Optional<Cart> targetElement = null;
        if (!CollectionUtils.isEmpty(cart)) {
            targetElement = cart.stream()
                    .filter(i -> Objects.equals(i.getProduct(), productId))
                    .findFirst();
        }
        return targetElement;
    }

    public int getQuantityCartItemByProductId(Long productId) throws ResourceNotFoundException {

        CarShopHelper helper = new CarShopHelper(session);
        Optional<Cart> cartOld = helper.getCartItemByProductId(productId);

        int oldQuantity = 0;
        if (!(cartOld == null || !cartOld.isPresent())) {
            oldQuantity = cartOld
                    .map(it -> it.getQuantity())
                    .orElse(0);
        }
        return oldQuantity;
    }

}
