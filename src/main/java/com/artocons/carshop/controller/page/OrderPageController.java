package com.artocons.carshop.controller.page;

import com.artocons.carshop.exception.ResourceNotFoundException;
import com.artocons.carshop.persistence.model.OrderItem;
import com.artocons.carshop.service.CartService;
import com.artocons.carshop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.math.BigDecimal;

import static com.artocons.carshop.util.CarShopConstants.ORDER_PATH;

@Controller
@RequestMapping(ORDER_PATH)
@RequiredArgsConstructor
public class OrderPageController {

    private static final String ORDER_PAGE = "orderPage";
    private static final String ORDER = "order";
    private static final BigDecimal DELIVERY = BigDecimal.valueOf(500.00);

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
        BigDecimal totalCost = subTotalCost.add(DELIVERY);

        model.addAttribute(ORDER, order);

        model.addAttribute("subTotal", subTotalCost);
        model.addAttribute("delivery", DELIVERY);
        model.addAttribute("total", totalCost);

        model.addAttribute("cartCount", cartService.getCartCount());    //userId
        model.addAttribute("cartTotalCost", subTotalCost);    //userId

        return ORDER_PAGE;
    }

    @GetMapping("/goBack")
    public String goBack(HttpSession session) {
        String referer = (String) session.getAttribute("previousCartPage");

        if (referer != null) {
            return "redirect:" + referer;
        } else {
            return "redirect:/cart";
        }
    }
}
