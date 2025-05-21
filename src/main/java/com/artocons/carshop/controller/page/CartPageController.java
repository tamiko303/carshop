package com.artocons.carshop.controller.page;

import com.artocons.carshop.exception.ResourceNotFoundException;
import com.artocons.carshop.persistence.dtos.CartItemDTO;
import com.artocons.carshop.service.AuthService;
import com.artocons.carshop.service.CartService;
import com.artocons.carshop.util.CarShopHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.artocons.carshop.util.CarShopConstants.*;

@Controller
@RequestMapping(CART_PATH)
@RequiredArgsConstructor
public class CartPageController {

    private static final String CART_PAGE = "cartPage";
    private static final String CART = "cart";

    private final CartService cartService;
    private final AuthService authService;

    @GetMapping
    public String getAllItemsCart(HttpServletRequest request,
                                  Model model) throws ResourceNotFoundException {

        CarShopHelper.setHistoryReferer(request);

        Page<CartItemDTO> cartItems = cartService.getCartPage(Pageable.unpaged());

        model.addAttribute(CART, cartItems);
        model.addAttribute("cartCount", cartService.getCartCount());
        model.addAttribute("cartTotalCost", cartService.getCartTotalCost());

        model.addAttribute("isAdmin", authService.getIsAdmin());

        return CART_PAGE;
    }

    @PostMapping("/{productId}/remove")
    public String removeProductFromCart(@PathVariable(value = "productId") long productId) throws ResourceNotFoundException {

        cartService.removeProductFromCart(productId);

        return "redirect:/cart";
    }

}
