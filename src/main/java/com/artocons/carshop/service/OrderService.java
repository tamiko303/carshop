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
        OrderHeader order = new OrderHeader();
        ValidOrderItems validOrderItems = createOrderItems(order);
        List<OrderItem> orderItems = validOrderItems.getValidItems();

        order.setOrderItems(new HashSet<>(orderItems));

        order.setFirstName(orderData.getFirstName());
        order.setLastName(orderData.getLastName());
        order.setAdress(orderData.getAdress());
        order.setPhone(orderData.getPhone());
        order.setDescription(orderData.getDescription());

        order.setSubTotal(calculateSubTotalAmount(orderItems));
        order.setDelivery(delivery);
        order.setTotal(order.getSubTotal().add(order.getDelivery()));

        OrderHeader saveOrder = orderRepository.save(order);
        cartService.clearCart();

        String message = validOrderItems.getMessage();
        session.setAttribute("orderMessage", message);

//        OrderHeaderDTO saveOrderDTO = MappingUtils.convertToOrderHeaderDTO(saveOrder, message);

        return saveOrder;
    }

    private ValidOrderItems createOrderItems(OrderHeader order) throws ResourceNotFoundException, ResourceVaidationException {
        List<OrderItem> itemsFromCart = formOrderItemsFromCart();

        ValidOrderItems validOrderItems = orderValidator.validate(itemsFromCart);

        validOrderItems.getValidItems().forEach(orderItem -> {
            orderItem.setOrder(order);
            try {
                stockService.reserveStock(orderItem.getProduct().getId(), orderItem.getQuantity());
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

}
