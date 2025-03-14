package com.artocons.carshop.controller.page;

import com.artocons.carshop.persistence.model.*;
import com.artocons.carshop.service.CarService;
import com.artocons.carshop.service.CartService;
import com.artocons.carshop.service.StockService;
import com.artocons.carshop.util.CarShopHelper;
import com.artocons.carshop.validation.QuantityValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

import static com.artocons.carshop.util.CarShopConstants.*;

@Controller
@RequestMapping(CARS_PATH)
public class CarListPageController {

    private static final String CAR_LIST_PAGE = "carListPage";
    private static final String CARS = "cars";
    private static final String SORT_FIED_DEFAULT = "price";
    private static final String SORT_DIR_DEFAULT = "asc";
    private static final int PAGE_START = 1;

    @Value("5")
    private Integer carsPerPage;

    @Resource
    private CarService carService;
    @Resource
    private StockService stockService;
    @Resource
    private CartService cartService;
    private QuantityValidator quantityValidator;

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

        Pageable pageRequest = PageRequest.of(pageNo - 1, carsPerPage, sort);

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

        model.addAttribute("cart", cartService.getCartList());
        model.addAttribute("cartCount", cartService.getCartCount());    //userId
        model.addAttribute("cartTotalCost", cartService.getCartTotalCost());    //userId

        return CAR_LIST_PAGE;
    }

    @PostMapping("/{productId}/add")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> addToCart(@PathVariable(value = "productId") long productId,
                                       @Valid @RequestBody int quantity,
                                       BindingResult errors) {

        AjaxResponse result = new AjaxResponse();
        quantityValidator.validate(quantity, errors);

        if (errors.hasErrors()) {
            result.setMsg(errors.getAllErrors()
                    .stream().map(x -> x.getDefaultMessage())
                    .collect(Collectors.joining(",")));
            return ResponseEntity.badRequest().body(result);
        }

        Cart cartItemNew = new Cart(productId, quantity, "" );
        ResultData newData = cartService.addItemToCart(cartItemNew);

        if (newData != null) {
            result.setMsg("success");
        } else {
            result.setMsg("no data found!");
        }

        result.setResult(newData);
        return (ResponseEntity<?>) ResponseEntity.ok(result);

    }
}
