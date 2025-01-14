package com.artocons.carshop.config;

import com.artocons.carshop.util.CarShopConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CarShopConfiguration implements WebMvcConfigurer {

    private static final String LOGIN_PAGE = "loginPage";
    private static final String ADMIN_PANEL_PAGE = "admin/adminPanelPage";

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController(CarShopConstants.LOGIN_PATH).setViewName(LOGIN_PAGE);
        registry.addViewController(CarShopConstants.ADMIN_PATH).setViewName(ADMIN_PANEL_PAGE);
        registry.addRedirectViewController(CarShopConstants.ROOT_PATH, CarShopConstants.CARS_PATH);
    }
}
