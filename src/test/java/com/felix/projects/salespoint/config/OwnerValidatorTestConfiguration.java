package com.felix.projects.salespoint.config;

import com.felix.projects.salespoint.validators.OwnerValidator;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("OwnerValidatorTest")
@Configuration
public class OwnerValidatorTestConfiguration {

  @Bean
  @Primary
  public OwnerValidator ownerValidator() {
    return Mockito.mock(OwnerValidator.class);
  }
}
