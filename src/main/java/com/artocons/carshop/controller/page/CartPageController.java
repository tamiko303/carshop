package com.artocons.carshop.controller.page;

import com.artocons.carshop.persistence.model.Cart;
import com.artocons.carshop.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

import static com.artocons.carshop.util.CarShopConstants.*;

@Controller
@RequestMapping(CART_PATH)
public class CartPageController {

    private static final String CART_PAGE = "cartPage";
    private static final String QUANTITY_DEFAULT = "1";

    @Resource
    private CartService cartService;

    @GetMapping
    public String viewCart(HttpSession session, Model model) {
        List<Cart> cart = (List<Cart>) session.getAttribute("cart");

        model.addAttribute("cart", cart);

        model.addAttribute("cartCount", cartService.getCartCount());    //userId
        model.addAttribute("cartTotalCost", cartService.getCartTotalCost());    //userId

        return CART_PAGE;
    }

    @PostMapping("/add/{productId}")
    public String addCarToCart(@PathVariable("productId") Long productId,
                               @RequestParam(defaultValue = QUANTITY_DEFAULT) int quantity,
                               HttpSession session) {

        Cart cartItem = new Cart(productId, quantity, Optional.of(""));

//        List<Cart> cartUpdate = cartService.addItemToCart(cartItem);

//        session.setAttribute("cart", cartUpdate);

        return "redirect:/cart";
    }

}
