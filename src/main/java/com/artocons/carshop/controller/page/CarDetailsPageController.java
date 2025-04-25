package com.artocons.carshop.controller.page;

import com.artocons.carshop.exception.ResourceNotFoundException;
import com.artocons.carshop.persistence.model.Car;
import com.artocons.carshop.service.CarService;
import com.artocons.carshop.service.CartService;
import com.artocons.carshop.util.CarShopHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;

import static com.artocons.carshop.util.CarShopConstants.PRODUCT_PATH;

@Controller
@RequestMapping(PRODUCT_PATH)
@RequiredArgsConstructor
public class CarDetailsPageController {

    private static final String CAR_DETAILS_PAGE = "carDetailsPage";
    private static final String CAR_ITEM = "carItem";

    private final CarService carService;
    private final CartService cartService;

    @GetMapping( "/{carId}")
    public String getCarDetails(@PathVariable("carId") Long carId,
                                HttpServletRequest request,
                                Model model) throws ResourceNotFoundException {

        CarShopHelper.setHistoryReferer(request);

        try {
            Car carDetails = carService.getCarById(carId);

            model.addAttribute(CAR_ITEM, carDetails);

            model.addAttribute("cartCount", cartService.getCartCount());    //userId
            model.addAttribute("cartTotalCost", cartService.getCartTotalCost());    //userId

        } catch (NoSuchElementException e){
            throw new ResourceNotFoundException(e.getMessage());
        }
        return CAR_DETAILS_PAGE;
    }

    @GetMapping("/goBack")
    public String goBack(HttpSession session) {
        String referer = (String) session.getAttribute("previousCarPage");

        return (referer != null) ? "redirect:" + referer : "redirect:/cars";
    }
}
