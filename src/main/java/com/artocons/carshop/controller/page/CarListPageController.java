package com.artocons.carshop.controller.page;

import com.artocons.carshop.persistence.model.Car;
import com.artocons.carshop.persistence.model.Cart;
import com.artocons.carshop.persistence.model.Stock;
import com.artocons.carshop.service.CarService;
import com.artocons.carshop.service.CartService;
import com.artocons.carshop.service.StockService;
import com.artocons.carshop.util.CarShopHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;

import static com.artocons.carshop.util.CarShopConstants.*;

@Controller
@RequestMapping(CARS_PATH)
public class CarListPageController {

    private static final String CAR_LIST_PAGE = "carListPage";
    private static final String CART_LIST_PAGE = "cartListPage";
    private static final String CARS = "cars";
    private static final String SORT_FIED_DEFAULT = "price";
    private static final String SORT_DIR_DEFAULT = "asc";
    private static final int PAGE_START = 1;

    @Value("${spring.pagination.cars-per-page}")
    private Integer carsPerPage;

    @Resource
    private CarService carService;
    @Resource
    private StockService stockService;
    @Resource
    private CartService cartService;

    @GetMapping
    public String getCarsList(Model model) {
//        model.addAttribute(CARS, carService.getCarsPage(Pageable.unpaged()));
//        return CAR_LIST_PAGE;

//        return findPaginatedList(1, SORT_FIED_DEFAULT, SORT_DIR_DEFAULT, "", model);
        return findAvailableCarsList(1, SORT_FIED_DEFAULT, SORT_DIR_DEFAULT, "", model);
    }

    @GetMapping("/page/{pageNo}")
    private String findAvailableCarsList(@PathVariable(value = "pageNo") int pageNo,
                                         @RequestParam(defaultValue = SORT_FIED_DEFAULT) String sortField,
                                         @RequestParam(defaultValue = SORT_DIR_DEFAULT) String sortDirection,
                                         @RequestParam("query") String query,
                                         Model model) {

        List<Car> cars = carService.getCarsFilteredByQuery(query);
        List<Stock> stocks = stockService.getAllAvailableCarsId();

        List<Car> availableCars = CarShopHelper.findIntersection(cars, stocks);

        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageRequest = PageRequest.of(PAGE_START - 1, carsPerPage, sort);

        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), availableCars.size());

        List<Car> pageContent = availableCars.subList(start, end);

        Page<Car> page = new PageImpl<>(pageContent, pageRequest, availableCars.size());

        model.addAttribute(CARS, page);

        model.addAttribute("query", query);

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDirection);
        model.addAttribute("reverseSortDir", sortDirection.equals("asc") ? "desc" : "asc");

        return CAR_LIST_PAGE;
    }

    @PostMapping("/add")
    public String addCarToCart(@RequestParam("userId") Long userId,
                               @RequestParam("productId") Long productId,
                               @RequestParam("quantity") int quantity,
                               HttpSession session) {

        cartService.addItemToCart(userId, productId, quantity, Optional.of(""));
        return "redirect:/";
    }
}
