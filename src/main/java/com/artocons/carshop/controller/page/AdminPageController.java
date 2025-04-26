package com.artocons.carshop.controller.page;

import com.artocons.carshop.exception.ResourceNotFoundException;
import com.artocons.carshop.persistence.enums.OrderStatus;
import com.artocons.carshop.persistence.model.OrderHeader;
import com.artocons.carshop.persistence.request.StatusRequest;
import com.artocons.carshop.persistence.response.StatusResponse;
import com.artocons.carshop.service.AuthService;
import com.artocons.carshop.service.OrderOverviewService;
import com.artocons.carshop.service.OrderService;
import com.artocons.carshop.util.CarShopHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
    private static final String ORDER_ID = "orderId";
    private static final String ORDER_STATUS = "orderStatus";

    private final OrderOverviewService orderOverviewService;
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
                                    Model model) {

        CarShopHelper.setHistoryReferer(request);

        OrderHeader order = orderOverviewService.getOrderByIdOrNull(orderId);

        model.addAttribute(ORDER, order.getOrderItems());
        model.addAttribute(ORDER_ID, orderId);
        model.addAttribute(ORDER_STATUS, order.getOrderStatus());

        CarShopHelper.setOrderDetailsAttribute(model, order);

        model.addAttribute("isAdmin", authService.getIsAdmin());
        model.addAttribute("isOrders", true);

        return ORDER_DETAILS_PAGE;
    }

    @GetMapping("/orders")
    public String goBack(HttpSession session) {
        String referer = (String) session.getAttribute("previousPage");

        return (referer != null) ? "redirect:" + referer : "redirect:/admin";
    }

    @PostMapping("/orders/{orderId}/setStatus")
    public ResponseEntity<StatusResponse> adminOrderSetStatus(@PathVariable("orderId") Long orderId,
                                                              @Valid @ModelAttribute StatusRequest data) {
        OrderStatus status;
        try {
            OrderStatus newStatus = data.getStatus();
            status = orderService.setStatus(orderId, newStatus);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new StatusResponse(e.getMessage(), "404", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new StatusResponse(e.getMessage(), "500", null));
        }
        return ResponseEntity.ok(new StatusResponse("", "200", status));
    }
}
