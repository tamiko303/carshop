package com.artocons.carshop.controller.page;

import com.artocons.carshop.persistence.model.Cart;
import com.artocons.carshop.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

import static com.artocons.carshop.util.CarShopConstants.*;

@Controller
@RequestMapping(CART_PATH)
public class CartPageController {

    private static final String CART_PAGE = "cartPage";

    @GetMapping
    public String getAllItemsCart(HttpSession session, Model model) {
        List<Cart> cart = (List<Cart>) session.getAttribute("cart");

        model.addAttribute("cart", cart);

        return CART_PAGE;
    }

}
