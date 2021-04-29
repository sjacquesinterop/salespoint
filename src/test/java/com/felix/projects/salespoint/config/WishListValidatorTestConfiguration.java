package com.felix.projects.salespoint.config;

import com.felix.projects.salespoint.validators.WishListValidator;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("wishListValidatorTest")
@Configuration
public class WishListValidatorTestConfiguration {

  @Bean
  @Primary
  public WishListValidator wishListValidator() {
    return Mockito.mock(WishListValidator.class);
  }
}
