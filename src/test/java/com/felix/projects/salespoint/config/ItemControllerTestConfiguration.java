package com.felix.projects.salespoint.config;

import com.felix.projects.salespoint.controller.ItemController;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("itemControllerTest")
@Configuration
public class ItemControllerTestConfiguration {

  @Bean
  @Primary
  public ItemController itemController() {
    return Mockito.mock(ItemController.class);
  }
}
