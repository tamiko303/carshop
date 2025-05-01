package com.artocons.carshop.config;

import com.artocons.carshop.util.CarShopConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CarShopConfiguration implements WebMvcConfigurer {

    private static final String LOGIN_PAGE = "loginPage";
    private static final String ADMIN_PANEL_PAGE = "admin/adminPanelPage";
    private static final String CART_PAGE = "cartPage";
    private static final String ORDER_PAGE = "orderPage";
    private static final String OVERVIEW_PAGE = "overviewPage";

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController(CarShopConstants.AUTH_PATH).setViewName(LOGIN_PAGE);
        registry.addViewController(CarShopConstants.ADMIN_PATH).setViewName(ADMIN_PANEL_PAGE);
        registry.addViewController(CarShopConstants.CART_PATH).setViewName(CART_PAGE);
        registry.addViewController(CarShopConstants.ORDER_PATH).setViewName(ORDER_PAGE);
        registry.addViewController(CarShopConstants.OVERVIEW_PATH).setViewName(OVERVIEW_PAGE);
        registry.addRedirectViewController(CarShopConstants.ROOT_PATH, CarShopConstants.CARS_PATH);
    }

}
