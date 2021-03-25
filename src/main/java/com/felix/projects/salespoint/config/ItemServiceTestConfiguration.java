package com.felix.projects.salespoint.config;

import com.felix.projects.salespoint.service.ItemService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("itemTest")
@Configuration
public class ItemServiceTestConfiguration {

  @Bean
  @Primary
  public ItemService itemService() {
    return Mockito.mock(ItemService.class);
  }
}
