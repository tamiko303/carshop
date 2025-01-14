package com.artocons.carshop.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.artocons.carshop.util.CarShopConstants.CARS_PATH;

@Controller
@RequestMapping(CARS_PATH)
public class CarDetailsPageController {

    private static final String CAR_DETAILS_PAGE = "carDetailsPage";

    @GetMapping( "/{carId}")
    public String getCarDetails(@PathVariable("carId") Long carId) {
        return CAR_DETAILS_PAGE;
    }
}
