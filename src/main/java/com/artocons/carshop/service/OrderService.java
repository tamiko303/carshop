package com.artocons.carshop.service;

import com.artocons.carshop.exception.ResourceNotFoundException;
import com.artocons.carshop.persistence.dtos.CartItemDTO;
import com.artocons.carshop.persistence.dtos.OrderItemDTO;
import com.artocons.carshop.persistence.model.*;
import com.artocons.carshop.persistence.repository.OrderRepository;
import com.artocons.carshop.util.MappingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartService cartService;
    private final CarService carService;
    private final OrderRepository orderRepository;


    public PageImpl<OrderItem> showOrderPage(Pageable pageable ) throws ResourceNotFoundException {
        List<Cart> cart = cartService.getCartList();

        List<OrderItem> orderItems = new ArrayList<>();

        cart.forEach(cartItem -> {
            Car product = carService.getCarByIdOrNull(cartItem.getProduct());
            if(product != null) {
                orderItems.add(new OrderItem( product, cartItem.getQuantity()));
            }
        });

        return new PageImpl<>(orderItems, pageable, orderItems.size());
    }


//    @Transactional
//    public Order placeOrder(customer) {
//        List<Cart> cart = (List<Cart>) session.getAttribute("cart");
//
//        Order order = createOrder(customer);
//        List<OrderItem> orderItems = createOrderItems(order, cart);
//
//        order.setOrderItems(new HashSet<>(orderItems));
//        order.setSubTotal(calculateSubTotal(orderItems));
//
//        Order saveOrder = orderRepository.save(order);
//        cartService.clearCart();
//
//        return saveOrder;
//    }

}
