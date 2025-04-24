package com.artocons.carshop.controller.page;

import com.artocons.carshop.exception.ResourceNotFoundException;
import com.artocons.carshop.persistence.model.OrderHeader;
import com.artocons.carshop.service.CartService;
import com.artocons.carshop.service.OrderOverviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.artocons.carshop.util.CarShopConstants.OVERVIEW_PATH;

@Controller
@RequestMapping(OVERVIEW_PATH)
@RequiredArgsConstructor
public class OrderOverviewPageController {

    private static final String OVERVIEW_PAGE = "overviewPage";
    private static final String ORDER = "order";
    private static final String ORDER_ID = "orderId";

    private final OrderOverviewService orderOverviewService;
    private final CartService cartService;

    @GetMapping(path = "/{orderId}")
    public String getOrderById(@PathVariable(value = "orderId") long orderId,
                               Model model) throws ResourceNotFoundException {

        OrderHeader order = orderOverviewService.getOrderByIdOrNull(orderId);
        String message = orderOverviewService.getMessage();

//        model.addAttribute(ORDER, order);

        model.addAttribute(ORDER, order.getOrderItems());
        model.addAttribute(ORDER_ID, orderId);

        model.addAttribute("showMsg", !message.isEmpty());
        model.addAttribute("message", message);

        model.addAttribute("subTotal", order.getSubTotal());
        model.addAttribute("delivery", order.getDelivery());
        model.addAttribute("total", order.getTotal());

        model.addAttribute("userName", order.getFirstName());
        model.addAttribute("userSuName", order.getLastName());
        model.addAttribute("userAdress", order.getAdress());
        model.addAttribute("userPhone", order.getPhone());
        model.addAttribute("userDescription", order.getDescription());

        model.addAttribute("cartCount", cartService.getCartCount());    //userId
        model.addAttribute("cartTotalCost", cartService.getCartTotalCost());    //userId

        return OVERVIEW_PAGE;
    }
}
