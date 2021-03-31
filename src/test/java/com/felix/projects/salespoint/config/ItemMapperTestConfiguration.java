package com.felix.projects.salespoint.config;

import com.felix.projects.salespoint.mapper.ItemMapper;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("itemMappertest")
@Configuration
public class ItemMapperTestConfiguration {

  @Bean
  @Primary
  public ItemMapper itemMapper() {
    return Mockito.mock(ItemMapper.class);
  }
}
