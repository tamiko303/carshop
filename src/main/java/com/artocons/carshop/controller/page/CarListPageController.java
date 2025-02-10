package com.artocons.carshop.controller.page;

import com.artocons.carshop.persistence.model.Car;
import com.artocons.carshop.service.CarService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

import static com.artocons.carshop.util.CarShopConstants.*;

@Controller
@RequestMapping(CARS_PATH)
public class CarListPageController {

    private static final String CAR_LIST_PAGE = "carListPage";
    private static final String CARS = "cars";

    @Value("${spring.mvc.pagination.cars-per-page}")
    private int carsPerPage;
    @Resource
    private CarService carService;

    @GetMapping
    public String getCarsList(Model model) {
//        model.addAttribute(CARS, carService.getCarsPage(Pageable.unpaged()));
//        return CAR_LIST_PAGE;

        return findPaginated(1, model);
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo, Model model) {

        Pageable pageable = PageRequest.of(pageNo - 1, carsPerPage);
        Page<Car> page = carService.getCarsPage(pageable);

        model.addAttribute(CARS, page);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());

        return CAR_LIST_PAGE;

    }
}
