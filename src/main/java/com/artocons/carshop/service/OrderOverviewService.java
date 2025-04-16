package com.artocons.carshop.service;

import com.artocons.carshop.persistence.model.*;
import com.artocons.carshop.persistence.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderOverviewService {

    private final OrderRepository orderRepository;

    public OrderHeader getOrderByIdOrNull(Long orderId) {

        return orderRepository.findById(orderId).orElse(null);
    }
}
