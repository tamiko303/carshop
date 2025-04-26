package com.artocons.carshop.controller.page;

import com.artocons.carshop.exception.ResourceNotFoundException;
import com.artocons.carshop.persistence.model.Car;
import com.artocons.carshop.persistence.model.OrderHeader;
import com.artocons.carshop.persistence.model.OrderItem;
import com.artocons.carshop.service.AuthService;
import com.artocons.carshop.service.OrderService;
import com.artocons.carshop.util.CarShopHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

import java.util.NoSuchElementException;

import static com.artocons.carshop.util.CarShopConstants.ADMIN_PATH;

@Controller
@RequestMapping(ADMIN_PATH)
@RequiredArgsConstructor
public class AdminPageController {

    private static final String ADMIN_PANEL_PAGE = "admin/adminPanelPage";
    private static final String ADMIN_ORDERS_PAGE = "admin/adminOrdersPage";
    private static final String ORDER_DETAILS_PAGE = "admin/orderDetailsPage";

    private static final String ORDERS = "orders";
    private static final String ORDER = "order";

    private final AuthService authService;
    private final OrderService orderService;

    @GetMapping()
    public String adminPanel(HttpServletRequest request,
                              Model model) {

        CarShopHelper.setHistoryReferer(request);

        model.addAttribute("isAdmin", authService.getIsAdmin());

        return ADMIN_PANEL_PAGE;
    }

    @GetMapping("/orders")
    public String adminOrders(Model model) {

        Page<OrderHeader> orders = orderService.getAllOrders(Pageable.unpaged());

        model.addAttribute(ORDERS, orders);
        model.addAttribute("isAdmin", authService.getIsAdmin());
        model.addAttribute("isOrders", true);

        return ADMIN_ORDERS_PAGE;
    }

    @GetMapping("/orders/{orderId}")
    public String adminOrderDetails(@PathVariable("orderId") Long orderId,
                                    HttpServletRequest request,
                                    Model model) throws ResourceNotFoundException {

        CarShopHelper.setHistoryReferer(request);

        try {

            OrderHeader orderDetails = orderService.getOrderByIdOrNull(orderId);

            model.addAttribute(ORDER, orderDetails);
            model.addAttribute("isAdmin", authService.getIsAdmin());
            model.addAttribute("isOrders", true);

        } catch (NoSuchElementException e){
            throw new ResourceNotFoundException(e.getMessage());
        }

        return ORDER_DETAILS_PAGE;
    }

    @PostMapping("/orders/{orderId}/setStatus")
    public String adminOrderSetStatus(@PathVariable("orderId") Long orderId) {

        return ORDER_DETAILS_PAGE;
    }
}
