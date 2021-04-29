package com.felix.projects.salespoint.config;

import com.felix.projects.salespoint.controller.WishListController;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("wishListControllerTest")
@Configuration
public class WishListControllerTestConfiguration {
  @Bean
  @Primary
  public WishListController wishListController() {
    return Mockito.mock(WishListController.class);
  }
}
