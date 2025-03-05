package com.artocons.carshop.controller.page;

import com.artocons.carshop.persistence.model.Car;
import com.artocons.carshop.service.CarService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
    private Page<Car> page;

    @Value("${spring.pagination.cars-per-page}")
    private Integer carsPerPage;

//    @Value("${spring.sort.car-defauit-field}")
//    private String sortFieldDefault;
//
//    @Value("${spring.sort.car-defauit-direction}")
//    private String sortDirDefault;

    @Resource
    private CarService carService;

    @GetMapping
    public String getCarsList(Model model) {
//        model.addAttribute(CARS, carService.getCarsPage(Pageable.unpaged()));
//        return CAR_LIST_PAGE;

        return findPaginatedList(1, SORT_FIED_DEFAULT, SORT_DIR_DEFAULT, "", model);
    }

    @GetMapping("/page/{pageNo}")
    private String findPaginatedList(@PathVariable(value = "pageNo") int pageNo,
                                     @RequestParam(defaultValue = SORT_FIED_DEFAULT) String sortField,
                                     @RequestParam(defaultValue = SORT_DIR_DEFAULT) String sortDirection,
                                     @RequestParam("query") String query,
                                     Model model) {

        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

//        Page<Car> searchRes = carService.searchCars(query, pageable);

        if (query.isEmpty()) {

            Pageable pageRequest = PageRequest.of(pageNo - 1, carsPerPage, sort);
            page = carService.getCarsPage(pageRequest);

        } else {

            List<Car> fiteredPage = carService.getCarsPage(Pageable.unpaged()).getContent()
                    .stream()
                    .filter(i -> i.getBrand().toLowerCase().contains(query.toLowerCase())
                              || i.getModel().toLowerCase().contains(query.toLowerCase()) )
                    .collect(Collectors.toList());

            Pageable pageRequest = PageRequest.of(PAGE_START - 1, carsPerPage, sort);

            int start = (int) pageRequest.getOffset();
            int end = Math.min((start + pageRequest.getPageSize()), fiteredPage.size());

            List<Car> pageContent = fiteredPage.subList(start, end);

            page = new PageImpl<Car>(pageContent, pageRequest, fiteredPage.size());
        }

        model.addAttribute(CARS, page);

        model.addAttribute("query", query);

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDirection);
        model.addAttribute("reverseSortDir", sortDirection.equals("asc") ? "desc" : "asc");

        return CAR_LIST_PAGE;

    }
}
