package com.artocons.carshop.controller.page;

import com.artocons.carshop.exception.ResourceNotFoundException;
import com.artocons.carshop.exception.ResourceVaidationException;
import com.artocons.carshop.persistence.model.OrderHeader;
import com.artocons.carshop.persistence.model.OrderItem;
import com.artocons.carshop.persistence.model.OrderRequest;
import com.artocons.carshop.service.CartService;
import com.artocons.carshop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.math.BigDecimal;

import static com.artocons.carshop.util.CarShopConstants.ORDER_PATH;

@Controller
@RequestMapping(ORDER_PATH)
@RequiredArgsConstructor
public class OrderPageController {

    @Value("${spring.delivery.price}")
    private BigDecimal delivery;

    private static final String ORDER_PAGE = "orderPage";
    private static final String ORDER = "order";

    private final OrderService orderService;
    private final CartService cartService;

    @GetMapping
    public String showOrder(HttpServletRequest request,
                            Model model) throws ResourceNotFoundException {

        HttpSession session = request.getSession();
        String previousPage = request.getHeader("Referer");
        session.setAttribute("previousCartPage", previousPage);

        Page<OrderItem> order = orderService.showOrderPage(Pageable.unpaged());

        BigDecimal subTotalCost = cartService.getCartTotalCost();   //userId
        BigDecimal totalCost = subTotalCost.add(delivery);

        model.addAttribute(ORDER, order);

        model.addAttribute("subTotal", subTotalCost);
        model.addAttribute("delivery", delivery);
        model.addAttribute("total", totalCost);

        return ORDER_PAGE;
    }

    @GetMapping("/goBack")
    public String goBack(HttpSession session) {
        String referer = (String) session.getAttribute("previousCartPage");

        return referer != null ? "redirect:" + referer : "redirect:/cart";
    }

    @PostMapping("/placeOrder")
    public String placeOrder(@Valid @ModelAttribute OrderRequest orderData) throws ResourceNotFoundException, ResourceVaidationException {

        OrderHeader savedOrder = orderService.placeOrder(orderData);

        return "redirect:/order-overview/" + savedOrder.getOrderId();
    }
}

