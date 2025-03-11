package com.artocons.carshop.controller.page;

import com.artocons.carshop.persistence.model.AjaxResponse;
import com.artocons.carshop.persistence.model.Cart;
import com.artocons.carshop.persistence.model.ResultData;
import com.artocons.carshop.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.artocons.carshop.util.CarShopConstants.*;

@Controller
@RequestMapping(CART_PATH)
public class CartPageController {

    private static final String CART_PAGE = "cartPage";
    private static final String QUANTITY_DEFAULT = "1";

    @Resource
    private CartService cartService;

    @GetMapping
    public String getAllItemsCart(HttpSession session, Model model) {
        List<Cart> cart = (List<Cart>) session.getAttribute("cart");

        model.addAttribute("cart", cart);

        return CART_PAGE;
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> addToCart( @Valid @RequestBody Cart newData,
                                                BindingResult errors) {

        AjaxResponse result = new AjaxResponse();

        if (errors.hasErrors()) {
            result.setMsg(errors.getAllErrors()
                    .stream().map(x -> x.getDefaultMessage())
                    .collect(Collectors.joining(",")));
            return ResponseEntity.badRequest().body(result);
        }

        Cart cartItemNew = new Cart(newData.getProduct(), newData.getQuantity(), "" );
        ResultData data = cartService.addItemToCart(cartItemNew);

        if (data != null) {
            result.setMsg("success");
        } else {
            result.setMsg("no data found!");
        }

        result.setResult(data);
        return (ResponseEntity<?>) ResponseEntity.ok();

    }

}
