package com.artocons.carshop.controller.page;

import com.artocons.carshop.persistence.request.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.artocons.carshop.util.CarShopConstants.ADMIN_PATH;
import static com.artocons.carshop.util.CarShopConstants.AUTH_PATH;

@Controller
@RequestMapping(AUTH_PATH)
@RequiredArgsConstructor
public class AuthController {

    private static final String LOGIN_PAGE = "loginPage";

    @GetMapping
    public String auth(HttpServletRequest request) {

        HttpSession session = request.getSession();
        String referer = request.getHeader("Referer");
        if (referer != null && referer.contains("Cars")) {
            session.setAttribute("previousCarPage", referer);
        }
        session.setAttribute("previousPage", referer);

        return LOGIN_PAGE;
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginRequest request) {

//        Authentication authenticationRequest =
//                UsernamePasswordAuthenticationToken.unauthenticated(request.getUsername(), request.getPassword());
//        Authentication authenticationResponse =
//                this.authenticationManager.authenticate(authenticationRequest);

//        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:" + ADMIN_PATH;
    }
}
