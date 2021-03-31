package com.felix.projects.salespoint.config;

import com.felix.projects.salespoint.validators.RoleValidator;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("roleValidatorTest")
@Configuration
public class RoleValidatorTestConfiguration {

  @Bean
  @Primary
  public RoleValidator roleValidator() {
    return Mockito.mock(RoleValidator.class);
  }
}
