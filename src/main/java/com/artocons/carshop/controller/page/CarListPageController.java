package com.artocons.carshop.controller.page;

import com.artocons.carshop.exception.ResourceNotFoundException;
import com.artocons.carshop.exception.ResourceVaidationException;
import com.artocons.carshop.persistence.model.*;
import com.artocons.carshop.service.CarService;
import com.artocons.carshop.service.CartService;
import com.artocons.carshop.service.StockService;
import com.artocons.carshop.validation.QuantityValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.artocons.carshop.util.CarShopConstants.*;
//import static java.lang.String.format;

@Controller
@RequestMapping(CARS_PATH)
@RequiredArgsConstructor
public class CarListPageController {

    private static final String CAR_LIST_PAGE = "carListPage";
    private static final String CARS = "cars";
    private static final String SORT_FIED_DEFAULT = "price";
    private static final String SORT_DIR_DEFAULT = "asc";

    private final CarService carService;
    private final CartService cartService;

    @GetMapping
    public String getCarsList(Model model) throws ResourceNotFoundException {
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
                                         Model model) throws ResourceNotFoundException {
        
        Page<Car> page = carService.getAvailableCarsList(query, pageNo, sortField, sortDirection, Pageable.unpaged());
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

    @PostMapping(path = "/{productId}/addToCart" )
    public ResponseEntity<AjaxResponse> addToCart(@PathVariable(value = "productId") long productId,
                                                  @Valid @ModelAttribute AjaxRequest data ) throws ResourceNotFoundException, ResourceVaidationException {

        ResultData newResData = new ResultData();
        try {
            newResData = cartService.addItemToCart(new Cart(productId, data.getQuantity(), ""));

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new AjaxResponse(e.getMessage(), "404", newResData));
        } catch (ResourceVaidationException e) {
//                System.out.println(format("Hi! I am error (%s)", e.getMessage()));
                return ResponseEntity.badRequest().body(new AjaxResponse(e.getMessage(), "422", newResData));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AjaxResponse(e.getMessage(), "500", newResData));
        }
        return ResponseEntity.ok(new AjaxResponse("", "200", newResData ));
    }
}
