package com.artocons.carshop.controller.page;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.artocons.carshop.util.CarShopConstants.LOGIN_PATH;

@Controller
@RequestMapping(LOGIN_PATH)
@RequiredArgsConstructor
public class AuthController {

    private static final String LOGIN_PAGE = "loginPage";

    @GetMapping
    public String login(HttpServletRequest request) {

        HttpSession session = request.getSession();
        String referer = request.getHeader("Referer");
        if (referer != null && referer.contains("Cars")) {
            session.setAttribute("previousCarPage", referer);
        }
        session.setAttribute("previousPage", referer);

        return LOGIN_PAGE;
    }
}
