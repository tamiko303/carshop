package com.artocons.carshop.controller.page;

import com.artocons.carshop.persistence.request.LoginRequest;
import com.artocons.carshop.util.CarShopHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.artocons.carshop.util.CarShopConstants.AUTH_PATH;

@Controller
@RequestMapping(AUTH_PATH)
@RequiredArgsConstructor
public class AuthController {

    private static final String LOGIN_PAGE = "loginPage";

    private final AuthenticationManager authenticationManager;

    @GetMapping
    public String auth(HttpServletRequest request) {

        CarShopHelper.setHistoryReferer(request);

        return LOGIN_PAGE;
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginRequest request) throws AuthenticationException {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

//        ShopUser shopUser = (ShopUser) authentication.getPrincipal();

        return "redirect:/admin/orders";
    }


}
