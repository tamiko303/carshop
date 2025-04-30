package com.artocons.carshop.service;

import com.artocons.carshop.exception.ResourceNotFoundException;
import com.artocons.carshop.exception.ResourceVaidationException;
import com.artocons.carshop.persistence.dtos.OrderHeaderDTO;
import com.artocons.carshop.persistence.model.*;
import com.artocons.carshop.persistence.repository.OrderRepository;
import com.artocons.carshop.util.MappingUtils;
import com.artocons.carshop.validation.OrderValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@Service
@RequiredArgsConstructor
public class OrderService {

    @Value("${spring.delivery.price}")
    private BigDecimal delivery;

    private final HttpSession session;
    private final CartService cartService;
    private final CarService carService;
    private final StockService stockService;
    private final OrderRepository orderRepository;
    private final OrderValidator orderValidator;

    public PageImpl<OrderItem> showOrderPage(Pageable pageable ) {
        List<OrderItem> orderItems = formOrderItemsFromCart();
        return new PageImpl<>(orderItems, pageable, orderItems.size());
    }

    @Transactional
    public OrderHeader placeOrder(OrderRequest orderData) throws ResourceNotFoundException, ResourceVaidationException {

        OrderHeader order = prepareOrderData(orderData);

        OrderHeader saveOrder = orderRepository.save(order);

        if (null != saveOrder.getOrderId()) {
            cartService.clearCart();
            session.setAttribute("order", saveOrder);
        }

        return saveOrder;
    }

    private ValidOrderItems createOrderItems(OrderHeader order) throws ResourceNotFoundException, ResourceVaidationException {
        List<OrderItem> itemsFromCart = formOrderItemsFromCart();

        ValidOrderItems validOrderItems = orderValidator.validate(itemsFromCart);

        List<OrderItem> itemsToSave = validOrderItems.getValidItems();

        itemsToSave.forEach(item -> {
            item.setOrder(order);
            try {
                stockService.reserveStock(item.getProduct().getId(), item.getQuantity());
            } catch (ResourceNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        return validOrderItems;
    }

    private List<OrderItem> formOrderItemsFromCart() {
        List<Cart> cart = cartService.getCartList();

        List<OrderItem> orderItems = new ArrayList<>();
        cart.forEach(cartItem -> {
            Car product = carService.getCarByIdOrNull(cartItem.getProduct());
            if(product != null) {
                orderItems.add(new OrderItem( product, cartItem.getQuantity()));
            }
        });

       return orderItems;
    }

    private BigDecimal calculateSubTotalAmount(List<OrderItem> orderItemList) {
        return  orderItemList
                .stream()
                .map(item -> item.getProduct().getPrice()
                        .multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private OrderHeader prepareOrderData(OrderRequest order) throws ResourceNotFoundException, ResourceVaidationException {

        OrderHeader newOrder = new OrderHeader();

        ValidOrderItems validOrderItems = createOrderItems(newOrder);

        newOrder.setOrderItems(new HashSet<>(validOrderItems.getValidItems()));

        newOrder.setFirstName(order.getFirstName());
        newOrder.setLastName(order.getLastName());
        newOrder.setAdress(order.getAdress());
        newOrder.setPhone(order.getPhone());
        newOrder.setDescription(order.getDescription());

        newOrder.setSubTotal(calculateSubTotalAmount(validOrderItems.getValidItems()));
        newOrder.setDelivery(delivery);
        newOrder.setTotal(newOrder.getSubTotal().add(newOrder.getDelivery()));

        return newOrder;
    }

}
