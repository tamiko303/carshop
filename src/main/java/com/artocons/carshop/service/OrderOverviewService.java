package com.artocons.carshop.service;

import com.artocons.carshop.persistence.model.*;
import com.artocons.carshop.persistence.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderOverviewService {

    private final OrderRepository orderRepository;
    private final HttpSession session;

    public OrderHeader getOrderByIdOrNull(Long orderId) {

        return orderRepository.findById(orderId).orElse(null);
    }

    public String getMessage(){
        return (String) session.getAttribute("orderMessage");
    }
}
