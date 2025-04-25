package com.artocons.carshop.controller.page;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.artocons.carshop.util.CarShopConstants.ADMIN_PATH;

@Controller
@RequestMapping(ADMIN_PATH)
public class AdminPageController {

    private static final String ADMIN_PANEL_PAGE = "adminPanelPage";
    private static final String ORDER_DETAILS_PAGE = "orderDetailsPage";

    @GetMapping("/orders")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminOrders() {

        return ADMIN_PANEL_PAGE;
    }

    @GetMapping("/orders/{orderId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminOrderDetails(@PathVariable("orderId") String orderId) {

        return ORDER_DETAILS_PAGE;
    }

    @GetMapping("/orders/{orderId}/changeStatus")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminOrderChangeStatus(@PathVariable("orderId") String orderId) {

        return ORDER_DETAILS_PAGE;
    }
}
