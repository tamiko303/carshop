package com.artocons.carshop.controller.page;

import com.artocons.carshop.exception.ResourceNotFoundException;
import com.artocons.carshop.persistence.model.Car;
import com.artocons.carshop.persistence.model.Rating;
import com.artocons.carshop.service.AuthService;
import com.artocons.carshop.service.CarService;
import com.artocons.carshop.service.CartService;
import com.artocons.carshop.service.RatingService;
import com.artocons.carshop.util.CarShopHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.NoSuchElementException;

import static com.artocons.carshop.util.CarShopConstants.PRODUCT_PATH;

@Controller
@RequestMapping(PRODUCT_PATH)
@RequiredArgsConstructor
public class CarDetailsPageController {

    private static final String CAR_DETAILS_PAGE = "carDetailsPage";
    private static final String CAR_COMMENTS_PAGE = "carCommentsPage";
    private static final String CAR_ITEM = "carItem";
    private static final String RATING_ITEM = "ratingDetailItem";

    private final CarService carService;
    private final CartService cartService;
    private final AuthService authService;
    private final RatingService ratingService;

    @GetMapping( "/{carId}")
    public String getCarDetails(@PathVariable("carId") Long carId,
                                HttpServletRequest request,
                                Model model) throws ResourceNotFoundException {

        CarShopHelper.setHistoryReferer(request);

        try {
            Car carDetails = carService.getCarByIdOrNull(carId);

            model.addAttribute(CAR_ITEM, carDetails);

            model.addAttribute("cartCount", cartService.getCartCount());
            model.addAttribute("cartTotalCost", cartService.getCartTotalCost());

            model.addAttribute("ratingStar", ratingService.calculateRatingStar(carId));
            model.addAttribute("averageRating", ratingService.calculateAverageRating(carId));
            model.addAttribute("ratingCount", ratingService.getRatingCount(carId));

            model.addAttribute("isAdmin", authService.getIsAdmin());

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

    @GetMapping("/{carId}/comments")
    public String getComments(@PathVariable("carId") Long carId,HttpServletRequest request,
                              Model model) throws ResourceNotFoundException {
        CarShopHelper.setHistoryReferer(request);

        try {
            Car carDetails = carService.getCarByIdOrNull(carId);

            List<Rating> ratingItems = ratingService.getRatingByProductId(carId);

            model.addAttribute(CAR_ITEM, carDetails);
            model.addAttribute(RATING_ITEM, ratingItems);

            model.addAttribute("ratingStar", ratingService.calculateRatingStar(carId));
            model.addAttribute("averageRating", ratingService.calculateAverageRating(carId));
            model.addAttribute("ratingCount", ratingService.getRatingCount(carId));

            model.addAttribute("isAdmin", authService.getIsAdmin());

        } catch (NoSuchElementException e){
            throw new ResourceNotFoundException(e.getMessage());
        }
        return CAR_COMMENTS_PAGE;
    }

    @PostMapping("/{carId}/rating")
    public String addRating(@PathVariable Long carId, @ModelAttribute Rating ratingData) {
        try {
            Rating savedOrder = ratingService.addRating(ratingData, carId);
            return String.format("redirect:/product/%d/comments", savedOrder.getProductId());
        } catch (NoSuchElementException e){
            return "redirect:/error";
        }


    }
}
