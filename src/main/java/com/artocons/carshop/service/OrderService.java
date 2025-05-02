package com.artocons.carshop.service;

import com.artocons.carshop.exception.ResourceNotFoundException;
import com.artocons.carshop.exception.ResourceVaidationException;
import com.artocons.carshop.persistence.dtos.OrderHeaderDTO;
import com.artocons.carshop.persistence.dtos.OrderItemDTO;
import com.artocons.carshop.persistence.enums.OrderStatus;
import com.artocons.carshop.persistence.model.*;
import com.artocons.carshop.persistence.repository.OrderRepository;
import com.artocons.carshop.persistence.request.OrderRequest;
import com.artocons.carshop.util.MappingUtils;
import com.artocons.carshop.validation.OrderValidator;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    @Value("${spring.delivery.price}")
    private BigDecimal delivery;

    private static final EnumSet<OrderStatus> STATUS_SET = EnumSet.allOf(OrderStatus.class);

    private final HttpSession session;
    private final CartService cartService;
    private final CarService carService;
    private final StockService stockService;
    private final OrderRepository orderRepository;
    private final OrderValidator orderValidator;

    public PageImpl<OrderItemDTO> showOrderPage(Pageable pageable ) {
        List<OrderItem> items = formOrderItemsFromCart();

        List<OrderItemDTO> orderItems = new ArrayList<>();
        items.forEach(item -> {
            Car product = carService.getCarByIdOrNull(item.getProduct());
            if (product != null) {
                orderItems.add(MappingUtils.convertToOrderItemDTO(item, product));
            }
        });

        return new PageImpl<>(orderItems, pageable, orderItems.size());
    }

    public Page<OrderHeader> getAllOrders(Pageable pageable ) {
        return orderRepository.findAll(pageable);
    }

    public OrderHeader getOrderByIdOrNull(Long orderId) {
        OrderHeader order = orderRepository.findById(orderId).orElse(null);
        assert order != null;
        Hibernate.initialize(order.getOrderItems());
        return order;
    }

    @Transactional
    public OrderStatus setStatus(Long orderId, OrderStatus status) throws ResourceNotFoundException {
        OrderHeader order = orderRepository.findById(orderId).orElse(null);
        if (order == null) {
            throw new ResourceNotFoundException("Order not found for id :: " + orderId);
        }
        OrderHeader saveOrder = new OrderHeader();
        if (STATUS_SET.contains(OrderStatus.valueOf(String.valueOf(status)))) {
            order.setOrderStatus(status);
            saveOrder = orderRepository.save(order);
        }
        return saveOrder.getOrderStatus();
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

    ValidOrderItems createOrderItems(OrderHeader order) throws ResourceNotFoundException, ResourceVaidationException {
        List<OrderItem> itemsFromCart = formOrderItemsFromCart();

        ValidOrderItems validOrderItems = orderValidator.validate(itemsFromCart);

        List<OrderItemDTO> itemsToSave = validOrderItems.getValidItems();

        itemsToSave.forEach(item -> {
//            item.setOrder(order.getOrderId());
            try {
                stockService.reserveStock(item.getProductId(), item.getQuantity());
            } catch (ResourceNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        return validOrderItems;
    }

    List<OrderItem> formOrderItemsFromCart() {
        List<Cart> cart = cartService.getCartList();

        List<OrderItem> orderItems = new ArrayList<>();
        cart.forEach(cartItem -> {
            Car product = carService.getCarByIdOrNull(cartItem.getProduct());
            if(product != null) {
                orderItems.add(new OrderItem( product.getId(), cartItem.getQuantity()));
            }
        });

       return orderItems;
    }

    BigDecimal calculateSubTotalAmount(List<OrderItem> orderItemList) {
        return  orderItemList
                .stream()
                .map(item -> {
                    try {
                        return carService.getPriceById(item.getProduct())
                                .multiply(new BigDecimal(item.getQuantity()));
                    } catch (ResourceNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private OrderHeader prepareOrderData(OrderRequest order) throws ResourceNotFoundException, ResourceVaidationException {

        OrderHeader newOrder = new OrderHeader();
        Set<OrderItem> orderItems = new HashSet<>();

        newOrder.setOrderStatus(OrderStatus.PENDING);
        newOrder.setOrderDate(LocalDate.now());

        ValidOrderItems validOrderItems = createOrderItems(newOrder);

        List<OrderItemDTO> itemsDto = validOrderItems.getValidItems();
        itemsDto.forEach(item -> {
            orderItems.add(MappingUtils.convertToOrderItemEntity(item, newOrder));
        });

        newOrder.setOrderItems(orderItems);

        newOrder.setFirstName(order.getFirstName());
        newOrder.setLastName(order.getLastName());
        newOrder.setAdress(order.getAdress());
        newOrder.setPhone(order.getPhone());
        newOrder.setDescription(order.getDescription());

        List<OrderItem> items = new ArrayList<>(orderItems);

        newOrder.setSubTotal(calculateSubTotalAmount(items));
        newOrder.setDelivery(delivery);
        newOrder.setTotal(newOrder.getSubTotal().add(newOrder.getDelivery()));

        return newOrder;
    }

}
