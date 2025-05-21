package com.artocons.carshop.util;

import com.artocons.carshop.persistence.enums.OrderStatus;
import com.artocons.carshop.persistence.model.OrderHeader;
import com.artocons.carshop.persistence.model.OrderItem;
import com.artocons.carshop.persistence.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@SpringBootTest
public class OrderTestHelper {

    @Autowired
    private CarRepository carRepository;

    public static OrderHeader createTestOrder(Long id, Set<OrderItem> orderItems) {

        return new OrderHeader(
                id,
                LocalDate.now(),
                OrderStatus.PENDING,
                BigDecimal.valueOf(130000.00),
                BigDecimal.valueOf(100.00),
                BigDecimal.valueOf(130100.00),
                "Pat",
                "John",
                "123 Main St",
                "2223111",
                "Test description",
                orderItems
        );
    }
}
