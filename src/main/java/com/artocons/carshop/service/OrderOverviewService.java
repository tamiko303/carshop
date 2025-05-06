package com.artocons.carshop.service;

import com.artocons.carshop.persistence.model.*;
import com.artocons.carshop.persistence.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class OrderOverviewService {

    private final HttpSession session;

    public void setOrderDetailsAttribute(Model model, OrderHeader order) {

        String errorIds = session.getAttribute("orderErrorIds") != null
                ? (String) session.getAttribute("orderErrorIds")
                : "";

        model.addAttribute("showMsg", !errorIds.isEmpty());
        model.addAttribute("message", errorIds);

        model.addAttribute("subTotal", order.getSubTotal());
        model.addAttribute("delivery", order.getDelivery());
        model.addAttribute("total", order.getTotal());

        model.addAttribute("userName", order.getFirstName());
        model.addAttribute("userSuName", order.getLastName());
        model.addAttribute("userAdress", order.getAdress());
        model.addAttribute("userPhone", order.getPhone());
        model.addAttribute("userDescription", order.getDescription());
    }
}
