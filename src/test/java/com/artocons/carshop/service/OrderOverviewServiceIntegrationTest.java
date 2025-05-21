package com.artocons.carshop.service;

import com.artocons.carshop.persistence.model.Car;
import com.artocons.carshop.persistence.model.OrderHeader;
import com.artocons.carshop.persistence.model.OrderItem;
import com.artocons.carshop.util.OrderTestHelper;
import com.artocons.carshop.util.ProductTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class OrderOverviewServiceIntegrationTest {

    @Autowired
    private OrderOverviewService orderOverviewService;

    @Autowired
    private HttpSession session;

    @BeforeEach
    void setUp() {
        session.setAttribute("orderErrorIds", null);
    }

    @Test
    void testOrderDetailsAttribute_WhenNoErrorIds() {

        Model model = new ExtendedModelMap();

        Set<OrderItem> orderItems = new HashSet<>();

        Car product1 = ProductTestHelper.createTestCar(1L, "Toyota", "Camry",  BigDecimal.valueOf(20000));
        OrderItem item1 = new OrderItem(product1.getId(), 2);

        Car product2 = ProductTestHelper.createTestCar(2L, "Honda", "Accord",  BigDecimal.valueOf(10000));
        OrderItem item2 = new OrderItem(product2.getId(), 3);

        orderItems.add(item1);
        orderItems.add(item2);

        OrderHeader order = OrderTestHelper.createTestOrder(1L, orderItems);

        orderOverviewService.setOrderDetailsAttribute(model, order);

        assertThat(model.getAttribute("showMsg")).isEqualTo(false);
        assertThat(model.getAttribute("message")).isEqualTo("");

        assertThat(model.getAttribute("subTotal")).isEqualTo(order.getSubTotal());
        assertThat(model.getAttribute("delivery")).isEqualTo(order.getDelivery());
        assertThat(model.getAttribute("total")).isEqualTo(order.getTotal());

        assertThat(model.getAttribute("userName")).isEqualTo(order.getFirstName());
        assertThat(model.getAttribute("userSuName")).isEqualTo(order.getLastName());
        assertThat(model.getAttribute("userAdress")).isEqualTo(order.getAdress());
        assertThat(model.getAttribute("userPhone")).isEqualTo(order.getPhone());
        assertThat(model.getAttribute("userDescription")).isEqualTo(order.getDescription());
    }

    @Test
    void setOrderDetailsAttribute_ShouldSetAttributesInModel_WhenErrorIdsExist() {
        Model model = new ExtendedModelMap();

        session.setAttribute("orderErrorIds", "someErrorIds");

        Set<OrderItem> orderItems = new HashSet<>();
        OrderHeader order = OrderTestHelper.createTestOrder(1L, orderItems);

        orderOverviewService.setOrderDetailsAttribute(model, order);

        assertThat(model.getAttribute("showMsg")).isEqualTo(true);
        assertThat(model.getAttribute("message")).isEqualTo("someErrorIds");
    }

}
