package com.artocons.carshop.controller.page;

import com.artocons.carshop.service.AuthService;
import com.artocons.carshop.util.CarShopHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

import static com.artocons.carshop.util.CarShopConstants.ADMIN_PATH;

@Controller
@RequestMapping(ADMIN_PATH)
@RequiredArgsConstructor
public class AdminPageController {

    private static final String ADMIN_PANEL_PAGE = "admin/adminPanelPage";
    private static final String ORDER_DETAILS_PAGE = "orderDetailsPage";

    private final AuthService authService;


    @GetMapping("/orders")
    public String adminOrders(HttpServletRequest request,
                              Model model) {

        CarShopHelper.setHistoryReferer(request);

        String string = "admin/orders";

        model.addAttribute("isAdmin", authService.getIsAdmin());

        return ADMIN_PANEL_PAGE;
    }

    @GetMapping("/orders/{orderId}")
    public String adminOrderDetails(@PathVariable("orderId") Long orderId) {

        return ORDER_DETAILS_PAGE;
    }

    @PostMapping("/orders/{orderId}/changeStatus")
    public String adminOrderChangeStatus(@PathVariable("orderId") Long orderId) {

        return ORDER_DETAILS_PAGE;
    }
}
