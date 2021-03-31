package com.felix.projects.salespoint.config;

import com.felix.projects.salespoint.controller.ExceptionHandlerController;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("exceptionHandlerController")
@Configuration
public class ExceptionHandlerControllerTestConfiguration {

  @Bean
  @Primary
  public ExceptionHandlerController exceptionHandlerController() {
    return Mockito.mock(ExceptionHandlerController.class);
  }
}
