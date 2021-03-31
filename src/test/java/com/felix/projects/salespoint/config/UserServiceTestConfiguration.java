package com.felix.projects.salespoint.config;

import com.felix.projects.salespoint.service.UserService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("userTest")
@Configuration
public class UserServiceTestConfiguration {

  @Bean
  @Primary
  public UserService userService() {
    return Mockito.mock(UserService.class);
  }
}
