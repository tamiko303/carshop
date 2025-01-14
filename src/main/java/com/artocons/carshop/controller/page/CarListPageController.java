package com.artocons.carshop.controller.page;

import com.artocons.carshop.service.CarService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

import static com.artocons.carshop.util.CarShopConstants.CARS_PATH;

@Controller
@RequestMapping(CARS_PATH)
public class CarListPageController {

    private static final String CAR_LIST_PAGE = "carListPage";
    private static final String CARS = "cars";

    @Resource
    private CarService carService;

    @GetMapping
    public String getCarsList(Model model) {
        model.addAttribute(CARS, carService.getCarsPage(Pageable.unpaged()));

        return CAR_LIST_PAGE;
    }
}
