package com.felix.projects.salespoint.config;

import com.felix.projects.salespoint.controller.UserController;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("userControllerTest")
@Configuration
public class UserControllerTestConfiguration {

  @Bean
  @Primary
  public UserController userController() {
    return Mockito.mock(UserController.class);
  }
}
