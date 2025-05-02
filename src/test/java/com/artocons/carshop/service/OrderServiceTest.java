package com.artocons.carshop.service;

import com.artocons.carshop.exception.ResourceNotFoundException;
import com.artocons.carshop.exception.ResourceVaidationException;
import com.artocons.carshop.persistence.enums.OrderStatus;
import com.artocons.carshop.persistence.model.*;
import com.artocons.carshop.persistence.repository.OrderRepository;
import com.artocons.carshop.validation.OrderValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceTest {
//    @Mock
//    private OrderRepository orderRepository;
//
//    @Mock
//    private CartService cartService;
//
//    @Mock
//    private OrderValidator orderValidator;
//
//    @Mock
//    private StockService stockService;
//
//    @Mock
//    private CarService carService;
//
//    @InjectMocks
//    private OrderService orderService;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//        orderService = new OrderService(null, null, null, null,   orderRepository,  null);
//    }
//
//    private OrderHeader createTestOrder(Long id) {
//        return new OrderHeader(
//                id,
//                LocalDate.now(),
//                OrderStatus.PENDING,
//                BigDecimal.valueOf(130000.00),
//                BigDecimal.valueOf(500.00),
//                BigDecimal.valueOf(130500.00),
//                "Pat",
//                "Sedan",
//                "Petrol-21",
//                "2223111",
//                "Automatic",
//                new HashSet<>()
//        );
//    }
//
//    @Test
//    public void testGetAllOrders() {
//        Pageable pageable = Pageable.unpaged();
//        OrderHeader order1 = createTestOrder(1L);
//        OrderHeader order2 = createTestOrder(2L);
//        Page<OrderHeader> expectedPage = new PageImpl<>(List.of(order1, order2));
//
//        when(orderRepository.findAll(pageable)).thenReturn(expectedPage);
//
//        Page<OrderHeader> actualPage = orderService.getAllOrders(pageable);
//
//        assertThat(actualPage).isEqualTo(expectedPage);
//    }
//
//    @Test
//    public void testSetStatus_OrderExists_ValidStatus() throws ResourceNotFoundException {
//        Long orderId = 1L;
//        OrderHeader existingOrder = createTestOrder(1L);
//        existingOrder.setOrderStatus(OrderStatus.PENDING);
//
//        when(orderRepository.findById(orderId)).thenReturn(Optional.of(existingOrder));
//        when(orderRepository.save(existingOrder)).thenReturn(existingOrder);
//
//        OrderStatus newStatus = OrderStatus.DELIVERED;
//        OrderStatus actualStatus = orderService.setStatus(orderId, newStatus);
//
//        assertThat(actualStatus).isEqualTo(newStatus);
//        assertThat(existingOrder.getOrderStatus()).isEqualTo(newStatus);
//
//        verify(orderRepository).save(existingOrder); // Убедитесь, что save был вызван
//    }
//    @Test
//    public void testSetStatus_OrderDoesNotExist() {
//        Long orderId = 1L;
//
//        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());
//
//        assertThrows(ResourceNotFoundException.class, () -> {
//            orderService.setStatus(orderId, OrderStatus.DELIVERED);
//        });
//
//        verify(orderRepository, never()).save(any());
//    }
//
//    @Test
//    public void testCreateOrderItems_ValidItems() throws ResourceNotFoundException, ResourceVaidationException {
//        OrderHeader order = createTestOrder(1L);
//        List<Cart> cartList = new ArrayList<>();
//        Cart cartItem = new Cart();
//        cartItem.setProduct(1L);
//        cartItem.setQuantity(2);
//        cartList.add(cartItem);
//
//        Car product = new Car();
//        product.setId(1L);
//        product.setPrice(new BigDecimal("100.00"));
//
//        ValidOrderItems validOrderItems = new ValidOrderItems();
//        List<OrderItem> validItems = new ArrayList<>();
//        OrderItem orderItem = new OrderItem(product, 2);
//        validItems.add(orderItem);
//        validOrderItems.setValidItems(validItems);
//
//        when(cartService.getCartList()).thenReturn(cartList);
//        when(carService.getCarByIdOrNull(1L)).thenReturn(product);
//        when(orderValidator.validate(any())).thenReturn(validOrderItems);
//
//        ValidOrderItems result = new ValidOrderItems();
//        try {
//            result = orderService.createOrderItems(order);
//        } catch (NullPointerException e) {
//            validOrderItems.setMessage("Stock not found");
//        }
//
//        assertNotNull(result);
//        assertEquals(1, result.getValidItems().size());
//
//        verify(stockService).reserveStock(product.getId(), orderItem.getQuantity());
//    }
//
//    @Test
//    public void testFormOrderItemsFromCart() {
//        List<Cart> cartList = new ArrayList<>();
//
//        Cart cartItem1 = new Cart();
//        cartItem1.setProduct(1L);
//        cartItem1.setQuantity(2);
//
//        Cart cartItem2 = new Cart();
//        cartItem2.setProduct(2L);
//        cartItem2.setQuantity(3);
//
//        cartList.add(cartItem1);
//        cartList.add(cartItem2);
//
//        Car product1 = new Car();
//        product1.setId(1L);
//
//        Car product2 = new Car();
//        product2.setId(2L);
//
//        when(cartService.getCartList()).thenReturn(cartList);
//
//        when(carService.getCarByIdOrNull(1L)).thenReturn(product1);
//
//        when(carService.getCarByIdOrNull(2L)).thenReturn(null);
//
//        List<OrderItem> result = new ArrayList<>();
//
//        cartList.forEach(cartItem -> {
//            Car product = carService.getCarByIdOrNull(cartItem.getProduct());
//            if(product != null) {
//                result.add(new OrderItem( product, cartItem.getQuantity()));
//            }
//        });
//
//        assertEquals(1, result.size());
//        assertEquals(product1, result.get(0).getProduct());
//        assertEquals(2, result.get(0).getQuantity());
//    }
//
//    @Test
//    public void testCalculateSubTotalAmount() {
//        List<OrderItem> orderItems = new ArrayList<>();
//
//        Car product1 = new Car();
//        product1.setPrice(new BigDecimal("100.00"));
//        OrderItem item1 = new OrderItem(product1, 2);
//
//        Car product2 = new Car();
//        product2.setPrice(new BigDecimal("50.00"));
//        OrderItem item2 = new OrderItem(product2, 3);
//
//        orderItems.add(item1);
//        orderItems.add(item2);
//
//        BigDecimal subtotal = orderService.calculateSubTotalAmount(orderItems);
//
//        assertEquals(new BigDecimal("350.00"), subtotal); // (100 * 2) + (50 * 3) = 200 + 150 = 350
//    }

}
