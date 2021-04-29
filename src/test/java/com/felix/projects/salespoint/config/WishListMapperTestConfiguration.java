package com.felix.projects.salespoint.config;

import com.felix.projects.salespoint.mapper.WishlistMapper;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("wishListMapperTest")
@Configuration
public class WishListMapperTestConfiguration {
  @Bean
  @Primary
  public WishlistMapper wishlistMapper() {
    return Mockito.mock(WishlistMapper.class);
  }
}
