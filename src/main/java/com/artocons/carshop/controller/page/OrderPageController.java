package com.artocons.carshop.controller.page;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.artocons.carshop.util.CarShopConstants.ORDER_PATH;

@Controller
@RequestMapping(ORDER_PATH)
@RequiredArgsConstructor
public class OrderPageController {

    private static final String ORDER_PAGE = "orderPage";

    @GetMapping
    public String makeOrder(Model model) {

        return ORDER_PAGE;
    }
}
