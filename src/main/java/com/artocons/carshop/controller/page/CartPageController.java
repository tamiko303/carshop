package com.artocons.carshop.controller.page;

import com.artocons.carshop.exception.ResourceNotFoundException;
import com.artocons.carshop.persistence.dtos.CartItemDTO;
import com.artocons.carshop.persistence.model.Car;
import com.artocons.carshop.persistence.model.Cart;
import com.artocons.carshop.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

import static com.artocons.carshop.util.CarShopConstants.*;

@Controller
@RequestMapping(CART_PATH)
@RequiredArgsConstructor
public class CartPageController {

    private static final String CART_PAGE = "cartPage";
    private static final String CART = "cart";

    private final CartService cartService;

    @GetMapping
    public String getAllItemsCart(HttpSession session, Model model) throws ResourceNotFoundException {
        Page<CartItemDTO> cartItems = cartService.getCartPage(Pageable.unpaged());

        model.addAttribute(CART, cartItems);
        model.addAttribute("cartCount", cartService.getCartCount());    //userId
        model.addAttribute("cartTotalCost", cartService.getCartTotalCost());    //userId

        return CART_PAGE;
    }

    @GetMapping("/{productId}/remove")
    private String removeProductFromCart(@PathVariable(value = "productId") long productId,
                                  Model model) throws ResourceNotFoundException {



        return "redirect:/cart";
    }

}
