package com.felix.projects.salespoint.config;

import com.felix.projects.salespoint.service.WishlistService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("wishListServiceTest")
@Configuration
public class WishListServiceTestConfiguration {

  @Bean
  @Primary
  public WishlistService wishlistService() {
    return Mockito.mock(WishlistService.class);
  }
}
