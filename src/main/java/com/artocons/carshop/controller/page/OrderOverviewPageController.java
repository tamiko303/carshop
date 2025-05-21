package com.artocons.carshop.controller.page;

import com.artocons.carshop.persistence.model.OrderHeader;
import com.artocons.carshop.service.OrderOverviewService;
import com.artocons.carshop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityNotFoundException;

import static com.artocons.carshop.util.CarShopConstants.OVERVIEW_PATH;

@Controller
@RequestMapping(OVERVIEW_PATH)
@RequiredArgsConstructor
public class OrderOverviewPageController {

    private static final String OVERVIEW_PAGE = "overviewPage";
    private static final String ORDER = "order";
    private static final String ORDER_ID = "orderId";

    private final OrderService orderService;
    private final OrderOverviewService orderOverviewService;

    @GetMapping(path = "/{orderId}")
    public String getOrderById(@PathVariable(value = "orderId") long orderId,
                               Model model){
        try {
            OrderHeader order = orderService.getOrderByIdOrNull(orderId);

            model.addAttribute(ORDER, order.getOrderItems());
            model.addAttribute(ORDER_ID, orderId);

            orderOverviewService.setOrderDetailsAttribute(model, order);

            return OVERVIEW_PAGE;
        } catch (EntityNotFoundException e) {
            return "error";
        }
    }
}
